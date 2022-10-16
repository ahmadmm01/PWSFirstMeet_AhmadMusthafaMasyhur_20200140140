/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompoknya.akmal.learnmigratedb;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MADD
 */
@Entity
@Table(name = "pustakawan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pustakawan.findAll", query = "SELECT p FROM Pustakawan p"),
    @NamedQuery(name = "Pustakawan.findByIDpustakawan", query = "SELECT p FROM Pustakawan p WHERE p.iDpustakawan = :iDpustakawan"),
    @NamedQuery(name = "Pustakawan.findByNamaPustakawan", query = "SELECT p FROM Pustakawan p WHERE p.namaPustakawan = :namaPustakawan"),
    @NamedQuery(name = "Pustakawan.findByNomorTelepon", query = "SELECT p FROM Pustakawan p WHERE p.nomorTelepon = :nomorTelepon"),
    @NamedQuery(name = "Pustakawan.findByTanggalLahir", query = "SELECT p FROM Pustakawan p WHERE p.tanggalLahir = :tanggalLahir"),
    @NamedQuery(name = "Pustakawan.findByJenisKelamin", query = "SELECT p FROM Pustakawan p WHERE p.jenisKelamin = :jenisKelamin"),
    @NamedQuery(name = "Pustakawan.findByAlamat", query = "SELECT p FROM Pustakawan p WHERE p.alamat = :alamat")})
public class Pustakawan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_pustakawan")
    private Integer iDpustakawan;
    @Basic(optional = false)
    @Column(name = "nama_pustakawan")
    private String namaPustakawan;
    @Basic(optional = false)
    @Column(name = "nomor_telepon")
    private String nomorTelepon;
    @Basic(optional = false)
    @Column(name = "tanggal_lahir")
    @Temporal(TemporalType.DATE)
    private Date tanggalLahir;
    @Basic(optional = false)
    @Column(name = "jenis_kelamin")
    private Character jenisKelamin;
    @Basic(optional = false)
    @Column(name = "alamat")
    private String alamat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDpustakawan")
    private Collection<Buku> bukuCollection;

    public Pustakawan() {
    }

    public Pustakawan(Integer iDpustakawan) {
        this.iDpustakawan = iDpustakawan;
    }

    public Pustakawan(Integer iDpustakawan, String namaPustakawan, String nomorTelepon, Date tanggalLahir, Character jenisKelamin, String alamat) {
        this.iDpustakawan = iDpustakawan;
        this.namaPustakawan = namaPustakawan;
        this.nomorTelepon = nomorTelepon;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
    }

    public Integer getIDpustakawan() {
        return iDpustakawan;
    }

    public void setIDpustakawan(Integer iDpustakawan) {
        this.iDpustakawan = iDpustakawan;
    }

    public String getNamaPustakawan() {
        return namaPustakawan;
    }

    public void setNamaPustakawan(String namaPustakawan) {
        this.namaPustakawan = namaPustakawan;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Character getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(Character jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @XmlTransient
    public Collection<Buku> getBukuCollection() {
        return bukuCollection;
    }

    public void setBukuCollection(Collection<Buku> bukuCollection) {
        this.bukuCollection = bukuCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDpustakawan != null ? iDpustakawan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pustakawan)) {
            return false;
        }
        Pustakawan other = (Pustakawan) object;
        if ((this.iDpustakawan == null && other.iDpustakawan != null) || (this.iDpustakawan != null && !this.iDpustakawan.equals(other.iDpustakawan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kelompoknya.akmal.learnmigratedb.Pustakawan[ iDpustakawan=" + iDpustakawan + " ]";
    }
    
}
