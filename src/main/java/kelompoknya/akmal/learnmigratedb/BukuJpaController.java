/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompoknya.akmal.learnmigratedb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import kelompoknya.akmal.learnmigratedb.exceptions.NonexistentEntityException;
import kelompoknya.akmal.learnmigratedb.exceptions.PreexistingEntityException;

/**
 *
 * @author MADD
 */
public class BukuJpaController implements Serializable {

    public BukuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Buku buku) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pustakawan IDpustakawan = buku.getIDpustakawan();
            if (IDpustakawan != null) {
                IDpustakawan = em.getReference(IDpustakawan.getClass(), IDpustakawan.getIDpustakawan());
                buku.setIDpustakawan(IDpustakawan);
            }
            Rak nomorRak = buku.getNomorRak();
            if (nomorRak != null) {
                nomorRak = em.getReference(nomorRak.getClass(), nomorRak.getNomorRak());
                buku.setNomorRak(nomorRak);
            }
            em.persist(buku);
            if (IDpustakawan != null) {
                IDpustakawan.getBukuCollection().add(buku);
                IDpustakawan = em.merge(IDpustakawan);
            }
            if (nomorRak != null) {
                nomorRak.getBukuCollection().add(buku);
                nomorRak = em.merge(nomorRak);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBuku(buku.getKodeBuku()) != null) {
                throw new PreexistingEntityException("Buku " + buku + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Buku buku) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buku persistentBuku = em.find(Buku.class, buku.getKodeBuku());
            Pustakawan IDpustakawanOld = persistentBuku.getIDpustakawan();
            Pustakawan IDpustakawanNew = buku.getIDpustakawan();
            Rak nomorRakOld = persistentBuku.getNomorRak();
            Rak nomorRakNew = buku.getNomorRak();
            if (IDpustakawanNew != null) {
                IDpustakawanNew = em.getReference(IDpustakawanNew.getClass(), IDpustakawanNew.getIDpustakawan());
                buku.setIDpustakawan(IDpustakawanNew);
            }
            if (nomorRakNew != null) {
                nomorRakNew = em.getReference(nomorRakNew.getClass(), nomorRakNew.getNomorRak());
                buku.setNomorRak(nomorRakNew);
            }
            buku = em.merge(buku);
            if (IDpustakawanOld != null && !IDpustakawanOld.equals(IDpustakawanNew)) {
                IDpustakawanOld.getBukuCollection().remove(buku);
                IDpustakawanOld = em.merge(IDpustakawanOld);
            }
            if (IDpustakawanNew != null && !IDpustakawanNew.equals(IDpustakawanOld)) {
                IDpustakawanNew.getBukuCollection().add(buku);
                IDpustakawanNew = em.merge(IDpustakawanNew);
            }
            if (nomorRakOld != null && !nomorRakOld.equals(nomorRakNew)) {
                nomorRakOld.getBukuCollection().remove(buku);
                nomorRakOld = em.merge(nomorRakOld);
            }
            if (nomorRakNew != null && !nomorRakNew.equals(nomorRakOld)) {
                nomorRakNew.getBukuCollection().add(buku);
                nomorRakNew = em.merge(nomorRakNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = buku.getKodeBuku();
                if (findBuku(id) == null) {
                    throw new NonexistentEntityException("The buku with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buku buku;
            try {
                buku = em.getReference(Buku.class, id);
                buku.getKodeBuku();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buku with id " + id + " no longer exists.", enfe);
            }
            Pustakawan IDpustakawan = buku.getIDpustakawan();
            if (IDpustakawan != null) {
                IDpustakawan.getBukuCollection().remove(buku);
                IDpustakawan = em.merge(IDpustakawan);
            }
            Rak nomorRak = buku.getNomorRak();
            if (nomorRak != null) {
                nomorRak.getBukuCollection().remove(buku);
                nomorRak = em.merge(nomorRak);
            }
            em.remove(buku);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buku> findBukuEntities() {
        return findBukuEntities(true, -1, -1);
    }

    public List<Buku> findBukuEntities(int maxResults, int firstResult) {
        return findBukuEntities(false, maxResults, firstResult);
    }

    private List<Buku> findBukuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buku.class));
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

    public Buku findBuku(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buku.class, id);
        } finally {
            em.close();
        }
    }

    public int getBukuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buku> rt = cq.from(Buku.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
