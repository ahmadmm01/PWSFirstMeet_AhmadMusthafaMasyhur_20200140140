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
import kelompoknya.akmal.learnmigratedb.exceptions.PreexistingEntityException;

/**
 *
 * @author MADD
 */
public class RakJpaController implements Serializable {

    public RakJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rak rak) throws PreexistingEntityException, Exception {
        if (rak.getBukuCollection() == null) {
            rak.setBukuCollection(new ArrayList<Buku>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Buku> attachedBukuCollection = new ArrayList<Buku>();
            for (Buku bukuCollectionBukuToAttach : rak.getBukuCollection()) {
                bukuCollectionBukuToAttach = em.getReference(bukuCollectionBukuToAttach.getClass(), bukuCollectionBukuToAttach.getKodeBuku());
                attachedBukuCollection.add(bukuCollectionBukuToAttach);
            }
            rak.setBukuCollection(attachedBukuCollection);
            em.persist(rak);
            for (Buku bukuCollectionBuku : rak.getBukuCollection()) {
                Rak oldNomorRakOfBukuCollectionBuku = bukuCollectionBuku.getNomorRak();
                bukuCollectionBuku.setNomorRak(rak);
                bukuCollectionBuku = em.merge(bukuCollectionBuku);
                if (oldNomorRakOfBukuCollectionBuku != null) {
                    oldNomorRakOfBukuCollectionBuku.getBukuCollection().remove(bukuCollectionBuku);
                    oldNomorRakOfBukuCollectionBuku = em.merge(oldNomorRakOfBukuCollectionBuku);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRak(rak.getNomorRak()) != null) {
                throw new PreexistingEntityException("Rak " + rak + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rak rak) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rak persistentRak = em.find(Rak.class, rak.getNomorRak());
            Collection<Buku> bukuCollectionOld = persistentRak.getBukuCollection();
            Collection<Buku> bukuCollectionNew = rak.getBukuCollection();
            List<String> illegalOrphanMessages = null;
            for (Buku bukuCollectionOldBuku : bukuCollectionOld) {
                if (!bukuCollectionNew.contains(bukuCollectionOldBuku)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Buku " + bukuCollectionOldBuku + " since its nomorRak field is not nullable.");
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
            rak.setBukuCollection(bukuCollectionNew);
            rak = em.merge(rak);
            for (Buku bukuCollectionNewBuku : bukuCollectionNew) {
                if (!bukuCollectionOld.contains(bukuCollectionNewBuku)) {
                    Rak oldNomorRakOfBukuCollectionNewBuku = bukuCollectionNewBuku.getNomorRak();
                    bukuCollectionNewBuku.setNomorRak(rak);
                    bukuCollectionNewBuku = em.merge(bukuCollectionNewBuku);
                    if (oldNomorRakOfBukuCollectionNewBuku != null && !oldNomorRakOfBukuCollectionNewBuku.equals(rak)) {
                        oldNomorRakOfBukuCollectionNewBuku.getBukuCollection().remove(bukuCollectionNewBuku);
                        oldNomorRakOfBukuCollectionNewBuku = em.merge(oldNomorRakOfBukuCollectionNewBuku);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = rak.getNomorRak();
                if (findRak(id) == null) {
                    throw new NonexistentEntityException("The rak with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rak rak;
            try {
                rak = em.getReference(Rak.class, id);
                rak.getNomorRak();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rak with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Buku> bukuCollectionOrphanCheck = rak.getBukuCollection();
            for (Buku bukuCollectionOrphanCheckBuku : bukuCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rak (" + rak + ") cannot be destroyed since the Buku " + bukuCollectionOrphanCheckBuku + " in its bukuCollection field has a non-nullable nomorRak field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rak);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rak> findRakEntities() {
        return findRakEntities(true, -1, -1);
    }

    public List<Rak> findRakEntities(int maxResults, int firstResult) {
        return findRakEntities(false, maxResults, firstResult);
    }

    private List<Rak> findRakEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rak.class));
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

    public Rak findRak(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rak.class, id);
        } finally {
            em.close();
        }
    }

    public int getRakCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rak> rt = cq.from(Rak.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
