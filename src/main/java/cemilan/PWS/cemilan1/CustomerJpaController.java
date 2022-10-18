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
import javax.persistence.Persistence;

/**
 *
 * @author Lenovo
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cemilan.PWS_cemilan1_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CustomerJpaController() {
    }
    
    

    public void create(Customer customer) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Order1 order1 = customer.getOrder1();
            if (order1 != null) {
                order1 = em.getReference(order1.getClass(), order1.getKodeOrder());
                customer.setOrder1(order1);
            }
            em.persist(customer);
            if (order1 != null) {
                Customer oldIdCustomerOfOrder1 = order1.getIdCustomer();
                if (oldIdCustomerOfOrder1 != null) {
                    oldIdCustomerOfOrder1.setOrder1(null);
                    oldIdCustomerOfOrder1 = em.merge(oldIdCustomerOfOrder1);
                }
                order1.setIdCustomer(customer);
                order1 = em.merge(order1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCustomer(customer.getIdCustomer()) != null) {
                throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getIdCustomer());
            Order1 order1Old = persistentCustomer.getOrder1();
            Order1 order1New = customer.getOrder1();
            List<String> illegalOrphanMessages = null;
            if (order1Old != null && !order1Old.equals(order1New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Order1 " + order1Old + " since its idCustomer field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (order1New != null) {
                order1New = em.getReference(order1New.getClass(), order1New.getKodeOrder());
                customer.setOrder1(order1New);
            }
            customer = em.merge(customer);
            if (order1New != null && !order1New.equals(order1Old)) {
                Customer oldIdCustomerOfOrder1 = order1New.getIdCustomer();
                if (oldIdCustomerOfOrder1 != null) {
                    oldIdCustomerOfOrder1.setOrder1(null);
                    oldIdCustomerOfOrder1 = em.merge(oldIdCustomerOfOrder1);
                }
                order1New.setIdCustomer(customer);
                order1New = em.merge(order1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = customer.getIdCustomer();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
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
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getIdCustomer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Order1 order1OrphanCheck = customer.getOrder1();
            if (order1OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Order1 " + order1OrphanCheck + " in its order1 field has a non-nullable idCustomer field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
