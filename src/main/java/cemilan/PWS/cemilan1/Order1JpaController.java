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
public class Order1JpaController implements Serializable {

    public Order1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Order1 order1) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Pegawai kodePegawaiOrphanCheck = order1.getKodePegawai();
        if (kodePegawaiOrphanCheck != null) {
            Order1 oldOrder1OfKodePegawai = kodePegawaiOrphanCheck.getOrder1();
            if (oldOrder1OfKodePegawai != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pegawai " + kodePegawaiOrphanCheck + " already has an item of type Order1 whose kodePegawai column cannot be null. Please make another selection for the kodePegawai field.");
            }
        }
        Customer idCustomerOrphanCheck = order1.getIdCustomer();
        if (idCustomerOrphanCheck != null) {
            Order1 oldOrder1OfIdCustomer = idCustomerOrphanCheck.getOrder1();
            if (oldOrder1OfIdCustomer != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Customer " + idCustomerOrphanCheck + " already has an item of type Order1 whose idCustomer column cannot be null. Please make another selection for the idCustomer field.");
            }
        }
        Kue kodeKueOrphanCheck = order1.getKodeKue();
        if (kodeKueOrphanCheck != null) {
            Order1 oldOrder1OfKodeKue = kodeKueOrphanCheck.getOrder1();
            if (oldOrder1OfKodeKue != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Kue " + kodeKueOrphanCheck + " already has an item of type Order1 whose kodeKue column cannot be null. Please make another selection for the kodeKue field.");
            }
        }
        Transaksi idTransaksiOrphanCheck = order1.getIdTransaksi();
        if (idTransaksiOrphanCheck != null) {
            Order1 oldOrder1OfIdTransaksi = idTransaksiOrphanCheck.getOrder1();
            if (oldOrder1OfIdTransaksi != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Transaksi " + idTransaksiOrphanCheck + " already has an item of type Order1 whose idTransaksi column cannot be null. Please make another selection for the idTransaksi field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai kodePegawai = order1.getKodePegawai();
            if (kodePegawai != null) {
                kodePegawai = em.getReference(kodePegawai.getClass(), kodePegawai.getKodePegawai());
                order1.setKodePegawai(kodePegawai);
            }
            Customer idCustomer = order1.getIdCustomer();
            if (idCustomer != null) {
                idCustomer = em.getReference(idCustomer.getClass(), idCustomer.getIdCustomer());
                order1.setIdCustomer(idCustomer);
            }
            Kue kodeKue = order1.getKodeKue();
            if (kodeKue != null) {
                kodeKue = em.getReference(kodeKue.getClass(), kodeKue.getKodeKue());
                order1.setKodeKue(kodeKue);
            }
            Transaksi idTransaksi = order1.getIdTransaksi();
            if (idTransaksi != null) {
                idTransaksi = em.getReference(idTransaksi.getClass(), idTransaksi.getIdTransaksi());
                order1.setIdTransaksi(idTransaksi);
            }
            em.persist(order1);
            if (kodePegawai != null) {
                kodePegawai.setOrder1(order1);
                kodePegawai = em.merge(kodePegawai);
            }
            if (idCustomer != null) {
                idCustomer.setOrder1(order1);
                idCustomer = em.merge(idCustomer);
            }
            if (kodeKue != null) {
                kodeKue.setOrder1(order1);
                kodeKue = em.merge(kodeKue);
            }
            if (idTransaksi != null) {
                idTransaksi.setOrder1(order1);
                idTransaksi = em.merge(idTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrder1(order1.getKodeOrder()) != null) {
                throw new PreexistingEntityException("Order1 " + order1 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Order1 order1) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Order1 persistentOrder1 = em.find(Order1.class, order1.getKodeOrder());
            Pegawai kodePegawaiOld = persistentOrder1.getKodePegawai();
            Pegawai kodePegawaiNew = order1.getKodePegawai();
            Customer idCustomerOld = persistentOrder1.getIdCustomer();
            Customer idCustomerNew = order1.getIdCustomer();
            Kue kodeKueOld = persistentOrder1.getKodeKue();
            Kue kodeKueNew = order1.getKodeKue();
            Transaksi idTransaksiOld = persistentOrder1.getIdTransaksi();
            Transaksi idTransaksiNew = order1.getIdTransaksi();
            List<String> illegalOrphanMessages = null;
            if (kodePegawaiNew != null && !kodePegawaiNew.equals(kodePegawaiOld)) {
                Order1 oldOrder1OfKodePegawai = kodePegawaiNew.getOrder1();
                if (oldOrder1OfKodePegawai != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pegawai " + kodePegawaiNew + " already has an item of type Order1 whose kodePegawai column cannot be null. Please make another selection for the kodePegawai field.");
                }
            }
            if (idCustomerNew != null && !idCustomerNew.equals(idCustomerOld)) {
                Order1 oldOrder1OfIdCustomer = idCustomerNew.getOrder1();
                if (oldOrder1OfIdCustomer != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Customer " + idCustomerNew + " already has an item of type Order1 whose idCustomer column cannot be null. Please make another selection for the idCustomer field.");
                }
            }
            if (kodeKueNew != null && !kodeKueNew.equals(kodeKueOld)) {
                Order1 oldOrder1OfKodeKue = kodeKueNew.getOrder1();
                if (oldOrder1OfKodeKue != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Kue " + kodeKueNew + " already has an item of type Order1 whose kodeKue column cannot be null. Please make another selection for the kodeKue field.");
                }
            }
            if (idTransaksiNew != null && !idTransaksiNew.equals(idTransaksiOld)) {
                Order1 oldOrder1OfIdTransaksi = idTransaksiNew.getOrder1();
                if (oldOrder1OfIdTransaksi != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Transaksi " + idTransaksiNew + " already has an item of type Order1 whose idTransaksi column cannot be null. Please make another selection for the idTransaksi field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (kodePegawaiNew != null) {
                kodePegawaiNew = em.getReference(kodePegawaiNew.getClass(), kodePegawaiNew.getKodePegawai());
                order1.setKodePegawai(kodePegawaiNew);
            }
            if (idCustomerNew != null) {
                idCustomerNew = em.getReference(idCustomerNew.getClass(), idCustomerNew.getIdCustomer());
                order1.setIdCustomer(idCustomerNew);
            }
            if (kodeKueNew != null) {
                kodeKueNew = em.getReference(kodeKueNew.getClass(), kodeKueNew.getKodeKue());
                order1.setKodeKue(kodeKueNew);
            }
            if (idTransaksiNew != null) {
                idTransaksiNew = em.getReference(idTransaksiNew.getClass(), idTransaksiNew.getIdTransaksi());
                order1.setIdTransaksi(idTransaksiNew);
            }
            order1 = em.merge(order1);
            if (kodePegawaiOld != null && !kodePegawaiOld.equals(kodePegawaiNew)) {
                kodePegawaiOld.setOrder1(null);
                kodePegawaiOld = em.merge(kodePegawaiOld);
            }
            if (kodePegawaiNew != null && !kodePegawaiNew.equals(kodePegawaiOld)) {
                kodePegawaiNew.setOrder1(order1);
                kodePegawaiNew = em.merge(kodePegawaiNew);
            }
            if (idCustomerOld != null && !idCustomerOld.equals(idCustomerNew)) {
                idCustomerOld.setOrder1(null);
                idCustomerOld = em.merge(idCustomerOld);
            }
            if (idCustomerNew != null && !idCustomerNew.equals(idCustomerOld)) {
                idCustomerNew.setOrder1(order1);
                idCustomerNew = em.merge(idCustomerNew);
            }
            if (kodeKueOld != null && !kodeKueOld.equals(kodeKueNew)) {
                kodeKueOld.setOrder1(null);
                kodeKueOld = em.merge(kodeKueOld);
            }
            if (kodeKueNew != null && !kodeKueNew.equals(kodeKueOld)) {
                kodeKueNew.setOrder1(order1);
                kodeKueNew = em.merge(kodeKueNew);
            }
            if (idTransaksiOld != null && !idTransaksiOld.equals(idTransaksiNew)) {
                idTransaksiOld.setOrder1(null);
                idTransaksiOld = em.merge(idTransaksiOld);
            }
            if (idTransaksiNew != null && !idTransaksiNew.equals(idTransaksiOld)) {
                idTransaksiNew.setOrder1(order1);
                idTransaksiNew = em.merge(idTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = order1.getKodeOrder();
                if (findOrder1(id) == null) {
                    throw new NonexistentEntityException("The order1 with id " + id + " no longer exists.");
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
            Order1 order1;
            try {
                order1 = em.getReference(Order1.class, id);
                order1.getKodeOrder();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The order1 with id " + id + " no longer exists.", enfe);
            }
            Pegawai kodePegawai = order1.getKodePegawai();
            if (kodePegawai != null) {
                kodePegawai.setOrder1(null);
                kodePegawai = em.merge(kodePegawai);
            }
            Customer idCustomer = order1.getIdCustomer();
            if (idCustomer != null) {
                idCustomer.setOrder1(null);
                idCustomer = em.merge(idCustomer);
            }
            Kue kodeKue = order1.getKodeKue();
            if (kodeKue != null) {
                kodeKue.setOrder1(null);
                kodeKue = em.merge(kodeKue);
            }
            Transaksi idTransaksi = order1.getIdTransaksi();
            if (idTransaksi != null) {
                idTransaksi.setOrder1(null);
                idTransaksi = em.merge(idTransaksi);
            }
            em.remove(order1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Order1> findOrder1Entities() {
        return findOrder1Entities(true, -1, -1);
    }

    public List<Order1> findOrder1Entities(int maxResults, int firstResult) {
        return findOrder1Entities(false, maxResults, firstResult);
    }

    private List<Order1> findOrder1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Order1.class));
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

    public Order1 findOrder1(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Order1.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrder1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Order1> rt = cq.from(Order1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
