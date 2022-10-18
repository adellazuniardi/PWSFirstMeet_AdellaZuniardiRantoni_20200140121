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
@Table(name = "pegawai")
@NamedQueries({
    @NamedQuery(name = "Pegawai.findAll", query = "SELECT p FROM Pegawai p"),
    @NamedQuery(name = "Pegawai.findByKodePegawai", query = "SELECT p FROM Pegawai p WHERE p.kodePegawai = :kodePegawai"),
    @NamedQuery(name = "Pegawai.findByNamaPegawai", query = "SELECT p FROM Pegawai p WHERE p.namaPegawai = :namaPegawai")})
public class Pegawai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_pegawai")
    private String kodePegawai;
    @Basic(optional = false)
    @Column(name = "nama_pegawai")
    private String namaPegawai;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kodePegawai")
    private Order1 order1;

    public Pegawai() {
    }

    public Pegawai(String kodePegawai) {
        this.kodePegawai = kodePegawai;
    }

    public Pegawai(String kodePegawai, String namaPegawai) {
        this.kodePegawai = kodePegawai;
        this.namaPegawai = namaPegawai;
    }

    public String getKodePegawai() {
        return kodePegawai;
    }

    public void setKodePegawai(String kodePegawai) {
        this.kodePegawai = kodePegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
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
        hash += (kodePegawai != null ? kodePegawai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pegawai)) {
            return false;
        }
        Pegawai other = (Pegawai) object;
        if ((this.kodePegawai == null && other.kodePegawai != null) || (this.kodePegawai != null && !this.kodePegawai.equals(other.kodePegawai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cemilan.PWS.cemilan1.Pegawai[ kodePegawai=" + kodePegawai + " ]";
    }
    
}
