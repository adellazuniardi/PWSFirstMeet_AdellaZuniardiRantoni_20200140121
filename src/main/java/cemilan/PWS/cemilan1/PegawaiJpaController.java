/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cemilan.PWS.cemilan1;

import cemilan.PWS.cemilan1.exceptions.IllegalOrphanException;
import cemilan.PWS.cemilan1.exceptions.NonexistentEntityException;
import cemilan.PWS.cemilan1.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Lenovo
 */
public class PegawaiJpaController implements Serializable {

    public PegawaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pegawai pegawai) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Order1 order1 = pegawai.getOrder1();
            if (order1 != null) {
                order1 = em.getReference(order1.getClass(), order1.getKodeOrder());
                pegawai.setOrder1(order1);
            }
            em.persist(pegawai);
            if (order1 != null) {
                Pegawai oldKodePegawaiOfOrder1 = order1.getKodePegawai();
                if (oldKodePegawaiOfOrder1 != null) {
                    oldKodePegawaiOfOrder1.setOrder1(null);
                    oldKodePegawaiOfOrder1 = em.merge(oldKodePegawaiOfOrder1);
                }
                order1.setKodePegawai(pegawai);
                order1 = em.merge(order1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPegawai(pegawai.getKodePegawai()) != null) {
                throw new PreexistingEntityException("Pegawai " + pegawai + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pegawai pegawai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai persistentPegawai = em.find(Pegawai.class, pegawai.getKodePegawai());
            Order1 order1Old = persistentPegawai.getOrder1();
            Order1 order1New = pegawai.getOrder1();
            List<String> illegalOrphanMessages = null;
            if (order1Old != null && !order1Old.equals(order1New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Order1 " + order1Old + " since its kodePegawai field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (order1New != null) {
                order1New = em.getReference(order1New.getClass(), order1New.getKodeOrder());
                pegawai.setOrder1(order1New);
            }
            pegawai = em.merge(pegawai);
            if (order1New != null && !order1New.equals(order1Old)) {
                Pegawai oldKodePegawaiOfOrder1 = order1New.getKodePegawai();
                if (oldKodePegawaiOfOrder1 != null) {
                    oldKodePegawaiOfOrder1.setOrder1(null);
                    oldKodePegawaiOfOrder1 = em.merge(oldKodePegawaiOfOrder1);
                }
                order1New.setKodePegawai(pegawai);
                order1New = em.merge(order1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pegawai.getKodePegawai();
                if (findPegawai(id) == null) {
                    throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.");
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
            Pegawai pegawai;
            try {
                pegawai = em.getReference(Pegawai.class, id);
                pegawai.getKodePegawai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Order1 order1OrphanCheck = pegawai.getOrder1();
            if (order1OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pegawai (" + pegawai + ") cannot be destroyed since the Order1 " + order1OrphanCheck + " in its order1 field has a non-nullable kodePegawai field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pegawai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pegawai> findPegawaiEntities() {
        return findPegawaiEntities(true, -1, -1);
    }

    public List<Pegawai> findPegawaiEntities(int maxResults, int firstResult) {
        return findPegawaiEntities(false, maxResults, firstResult);
    }

    private List<Pegawai> findPegawaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pegawai.class));
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

    public Pegawai findPegawai(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pegawai.class, id);
        } finally {
            em.close();
        }
    }

    public int getPegawaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pegawai> rt = cq.from(Pegawai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
