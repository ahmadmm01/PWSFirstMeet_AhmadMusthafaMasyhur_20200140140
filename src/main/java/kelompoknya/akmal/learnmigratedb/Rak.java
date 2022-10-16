/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompoknya.akmal.learnmigratedb;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MADD
 */
@Entity
@Table(name = "rak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rak.findAll", query = "SELECT r FROM Rak r"),
    @NamedQuery(name = "Rak.findByNomorRak", query = "SELECT r FROM Rak r WHERE r.nomorRak = :nomorRak"),
    @NamedQuery(name = "Rak.findByKategoriRak", query = "SELECT r FROM Rak r WHERE r.kategoriRak = :kategoriRak"),
    @NamedQuery(name = "Rak.findByLokasiRak", query = "SELECT r FROM Rak r WHERE r.lokasiRak = :lokasiRak")})
public class Rak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nomor_rak")
    private String nomorRak;
    @Basic(optional = false)
    @Column(name = "kategori_rak")
    private String kategoriRak;
    @Basic(optional = false)
    @Column(name = "lokasi_rak")
    private String lokasiRak;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nomorRak")
    private Collection<Buku> bukuCollection;

    public Rak() {
    }

    public Rak(String nomorRak) {
        this.nomorRak = nomorRak;
    }

    public Rak(String nomorRak, String kategoriRak, String lokasiRak) {
        this.nomorRak = nomorRak;
        this.kategoriRak = kategoriRak;
        this.lokasiRak = lokasiRak;
    }

    public String getNomorRak() {
        return nomorRak;
    }

    public void setNomorRak(String nomorRak) {
        this.nomorRak = nomorRak;
    }

    public String getKategoriRak() {
        return kategoriRak;
    }

    public void setKategoriRak(String kategoriRak) {
        this.kategoriRak = kategoriRak;
    }

    public String getLokasiRak() {
        return lokasiRak;
    }

    public void setLokasiRak(String lokasiRak) {
        this.lokasiRak = lokasiRak;
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
        hash += (nomorRak != null ? nomorRak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rak)) {
            return false;
        }
        Rak other = (Rak) object;
        if ((this.nomorRak == null && other.nomorRak != null) || (this.nomorRak != null && !this.nomorRak.equals(other.nomorRak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kelompoknya.akmal.learnmigratedb.Rak[ nomorRak=" + nomorRak + " ]";
    }
    
}
