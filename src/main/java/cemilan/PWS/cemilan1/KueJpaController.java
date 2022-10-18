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
public class KueJpaController implements Serializable {

    public KueJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = ;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kue kue) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Order1 order1 = kue.getOrder1();
            if (order1 != null) {
                order1 = em.getReference(order1.getClass(), order1.getKodeOrder());
                kue.setOrder1(order1);
            }
            em.persist(kue);
            if (order1 != null) {
                Kue oldKodeKueOfOrder1 = order1.getKodeKue();
                if (oldKodeKueOfOrder1 != null) {
                    oldKodeKueOfOrder1.setOrder1(null);
                    oldKodeKueOfOrder1 = em.merge(oldKodeKueOfOrder1);
                }
                order1.setKodeKue(kue);
                order1 = em.merge(order1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKue(kue.getKodeKue()) != null) {
                throw new PreexistingEntityException("Kue " + kue + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kue kue) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kue persistentKue = em.find(Kue.class, kue.getKodeKue());
            Order1 order1Old = persistentKue.getOrder1();
            Order1 order1New = kue.getOrder1();
            List<String> illegalOrphanMessages = null;
            if (order1Old != null && !order1Old.equals(order1New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Order1 " + order1Old + " since its kodeKue field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (order1New != null) {
                order1New = em.getReference(order1New.getClass(), order1New.getKodeOrder());
                kue.setOrder1(order1New);
            }
            kue = em.merge(kue);
            if (order1New != null && !order1New.equals(order1Old)) {
                Kue oldKodeKueOfOrder1 = order1New.getKodeKue();
                if (oldKodeKueOfOrder1 != null) {
                    oldKodeKueOfOrder1.setOrder1(null);
                    oldKodeKueOfOrder1 = em.merge(oldKodeKueOfOrder1);
                }
                order1New.setKodeKue(kue);
                order1New = em.merge(order1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kue.getKodeKue();
                if (findKue(id) == null) {
                    throw new NonexistentEntityException("The kue with id " + id + " no longer exists.");
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
            Kue kue;
            try {
                kue = em.getReference(Kue.class, id);
                kue.getKodeKue();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kue with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Order1 order1OrphanCheck = kue.getOrder1();
            if (order1OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kue (" + kue + ") cannot be destroyed since the Order1 " + order1OrphanCheck + " in its order1 field has a non-nullable kodeKue field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(kue);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kue> findKueEntities() {
        return findKueEntities(true, -1, -1);
    }

    public List<Kue> findKueEntities(int maxResults, int firstResult) {
        return findKueEntities(false, maxResults, firstResult);
    }

    private List<Kue> findKueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kue.class));
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

    public Kue findKue(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kue.class, id);
        } finally {
            em.close();
        }
    }

    public int getKueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kue> rt = cq.from(Kue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
