/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cemilan.PWS.cemilan1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "order")
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findByKodeOrder", query = "SELECT o FROM Order1 o WHERE o.kodeOrder = :kodeOrder")})
public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_order")
    private String kodeOrder;
    @JoinColumn(name = "kode_pegawai", referencedColumnName = "kode_pegawai")
    @OneToOne(optional = false)
    private Pegawai kodePegawai;
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer")
    @OneToOne(optional = false)
    private Customer idCustomer;
    @JoinColumn(name = "kode_kue", referencedColumnName = "kode_kue")
    @OneToOne(optional = false)
    private Kue kodeKue;
    @JoinColumn(name = "id_transaksi", referencedColumnName = "id_transaksi")
    @OneToOne(optional = false)
    private Transaksi idTransaksi;

    public Order1() {
    }

    public Order1(String kodeOrder) {
        this.kodeOrder = kodeOrder;
    }

    public String getKodeOrder() {
        return kodeOrder;
    }

    public void setKodeOrder(String kodeOrder) {
        this.kodeOrder = kodeOrder;
    }

    public Pegawai getKodePegawai() {
        return kodePegawai;
    }

    public void setKodePegawai(Pegawai kodePegawai) {
        this.kodePegawai = kodePegawai;
    }

    public Customer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Customer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Kue getKodeKue() {
        return kodeKue;
    }

    public void setKodeKue(Kue kodeKue) {
        this.kodeKue = kodeKue;
    }

    public Transaksi getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Transaksi idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeOrder != null ? kodeOrder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.kodeOrder == null && other.kodeOrder != null) || (this.kodeOrder != null && !this.kodeOrder.equals(other.kodeOrder))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemilan.PWS.cemilan1.Order1[ kodeOrder=" + kodeOrder + " ]";
    }
    
}
