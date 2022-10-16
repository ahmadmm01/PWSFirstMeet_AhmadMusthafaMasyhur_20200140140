/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompoknya.akmal.learnmigratedb;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import kelompoknya.akmal.learnmigratedb.exceptions.IllegalOrphanException;
import kelompoknya.akmal.learnmigratedb.exceptions.NonexistentEntityException;

/**
 *
 * @author MADD
 */
public class PustakawanJpaController implements Serializable {

    public PustakawanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pustakawan pustakawan) {
        if (pustakawan.getBukuCollection() == null) {
            pustakawan.setBukuCollection(new ArrayList<Buku>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Buku> attachedBukuCollection = new ArrayList<Buku>();
            for (Buku bukuCollectionBukuToAttach : pustakawan.getBukuCollection()) {
                bukuCollectionBukuToAttach = em.getReference(bukuCollectionBukuToAttach.getClass(), bukuCollectionBukuToAttach.getKodeBuku());
                attachedBukuCollection.add(bukuCollectionBukuToAttach);
            }
            pustakawan.setBukuCollection(attachedBukuCollection);
            em.persist(pustakawan);
            for (Buku bukuCollectionBuku : pustakawan.getBukuCollection()) {
                Pustakawan oldIDpustakawanOfBukuCollectionBuku = bukuCollectionBuku.getIDpustakawan();
                bukuCollectionBuku.setIDpustakawan(pustakawan);
                bukuCollectionBuku = em.merge(bukuCollectionBuku);
                if (oldIDpustakawanOfBukuCollectionBuku != null) {
                    oldIDpustakawanOfBukuCollectionBuku.getBukuCollection().remove(bukuCollectionBuku);
                    oldIDpustakawanOfBukuCollectionBuku = em.merge(oldIDpustakawanOfBukuCollectionBuku);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pustakawan pustakawan) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pustakawan persistentPustakawan = em.find(Pustakawan.class, pustakawan.getIDpustakawan());
            Collection<Buku> bukuCollectionOld = persistentPustakawan.getBukuCollection();
            Collection<Buku> bukuCollectionNew = pustakawan.getBukuCollection();
            List<String> illegalOrphanMessages = null;
            for (Buku bukuCollectionOldBuku : bukuCollectionOld) {
                if (!bukuCollectionNew.contains(bukuCollectionOldBuku)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Buku " + bukuCollectionOldBuku + " since its IDpustakawan field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Buku> attachedBukuCollectionNew = new ArrayList<Buku>();
            for (Buku bukuCollectionNewBukuToAttach : bukuCollectionNew) {
                bukuCollectionNewBukuToAttach = em.getReference(bukuCollectionNewBukuToAttach.getClass(), bukuCollectionNewBukuToAttach.getKodeBuku());
                attachedBukuCollectionNew.add(bukuCollectionNewBukuToAttach);
            }
            bukuCollectionNew = attachedBukuCollectionNew;
            pustakawan.setBukuCollection(bukuCollectionNew);
            pustakawan = em.merge(pustakawan);
            for (Buku bukuCollectionNewBuku : bukuCollectionNew) {
                if (!bukuCollectionOld.contains(bukuCollectionNewBuku)) {
                    Pustakawan oldIDpustakawanOfBukuCollectionNewBuku = bukuCollectionNewBuku.getIDpustakawan();
                    bukuCollectionNewBuku.setIDpustakawan(pustakawan);
                    bukuCollectionNewBuku = em.merge(bukuCollectionNewBuku);
                    if (oldIDpustakawanOfBukuCollectionNewBuku != null && !oldIDpustakawanOfBukuCollectionNewBuku.equals(pustakawan)) {
                        oldIDpustakawanOfBukuCollectionNewBuku.getBukuCollection().remove(bukuCollectionNewBuku);
                        oldIDpustakawanOfBukuCollectionNewBuku = em.merge(oldIDpustakawanOfBukuCollectionNewBuku);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pustakawan.getIDpustakawan();
                if (findPustakawan(id) == null) {
                    throw new NonexistentEntityException("The pustakawan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pustakawan pustakawan;
            try {
                pustakawan = em.getReference(Pustakawan.class, id);
                pustakawan.getIDpustakawan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pustakawan with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Buku> bukuCollectionOrphanCheck = pustakawan.getBukuCollection();
            for (Buku bukuCollectionOrphanCheckBuku : bukuCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pustakawan (" + pustakawan + ") cannot be destroyed since the Buku " + bukuCollectionOrphanCheckBuku + " in its bukuCollection field has a non-nullable IDpustakawan field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pustakawan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pustakawan> findPustakawanEntities() {
        return findPustakawanEntities(true, -1, -1);
    }

    public List<Pustakawan> findPustakawanEntities(int maxResults, int firstResult) {
        return findPustakawanEntities(false, maxResults, firstResult);
    }

    private List<Pustakawan> findPustakawanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pustakawan.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pustakawan findPustakawan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pustakawan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPustakawanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pustakawan> rt = cq.from(Pustakawan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
