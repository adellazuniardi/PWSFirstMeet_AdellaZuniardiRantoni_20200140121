/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cemilan.PWS.cemilan1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "kue")
@NamedQueries({
    @NamedQuery(name = "Kue.findAll", query = "SELECT k FROM Kue k"),
    @NamedQuery(name = "Kue.findByKodeKue", query = "SELECT k FROM Kue k WHERE k.kodeKue = :kodeKue"),
    @NamedQuery(name = "Kue.findByJenisKue", query = "SELECT k FROM Kue k WHERE k.jenisKue = :jenisKue"),
    @NamedQuery(name = "Kue.findByNamaKue", query = "SELECT k FROM Kue k WHERE k.namaKue = :namaKue")})
public class Kue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_kue")
    private String kodeKue;
    @Basic(optional = false)
    @Column(name = "jenis_kue")
    private String jenisKue;
    @Basic(optional = false)
    @Column(name = "nama_kue")
    private String namaKue;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kodeKue")
    private Order1 order1;

    public Kue() {
    }

    public Kue(String kodeKue) {
        this.kodeKue = kodeKue;
    }

    public Kue(String kodeKue, String jenisKue, String namaKue) {
        this.kodeKue = kodeKue;
        this.jenisKue = jenisKue;
        this.namaKue = namaKue;
    }

    public String getKodeKue() {
        return kodeKue;
    }

    public void setKodeKue(String kodeKue) {
        this.kodeKue = kodeKue;
    }

    public String getJenisKue() {
        return jenisKue;
    }

    public void setJenisKue(String jenisKue) {
        this.jenisKue = jenisKue;
    }

    public String getNamaKue() {
        return namaKue;
    }

    public void setNamaKue(String namaKue) {
        this.namaKue = namaKue;
    }

    public Order1 getOrder1() {
        return order1;
    }

    public void setOrder1(Order1 order1) {
        this.order1 = order1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeKue != null ? kodeKue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kue)) {
            return false;
        }
        Kue other = (Kue) object;
        if ((this.kodeKue == null && other.kodeKue != null) || (this.kodeKue != null && !this.kodeKue.equals(other.kodeKue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemilan.PWS.cemilan1.Kue[ kodeKue=" + kodeKue + " ]";
    }
    
}
