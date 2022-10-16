/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompoknya.akmal.learnmigratedb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MADD
 */
@Entity
@Table(name = "buku")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buku.findAll", query = "SELECT b FROM Buku b"),
    @NamedQuery(name = "Buku.findByKodeBuku", query = "SELECT b FROM Buku b WHERE b.kodeBuku = :kodeBuku"),
    @NamedQuery(name = "Buku.findByPenulisBuku", query = "SELECT b FROM Buku b WHERE b.penulisBuku = :penulisBuku"),
    @NamedQuery(name = "Buku.findByJudulBuku", query = "SELECT b FROM Buku b WHERE b.judulBuku = :judulBuku"),
    @NamedQuery(name = "Buku.findByIsbn", query = "SELECT b FROM Buku b WHERE b.isbn = :isbn"),
    @NamedQuery(name = "Buku.findByPenerbit", query = "SELECT b FROM Buku b WHERE b.penerbit = :penerbit"),
    @NamedQuery(name = "Buku.findByEksemplarBuku", query = "SELECT b FROM Buku b WHERE b.eksemplarBuku = :eksemplarBuku"),
    @NamedQuery(name = "Buku.findBySubjek", query = "SELECT b FROM Buku b WHERE b.subjek = :subjek"),
    @NamedQuery(name = "Buku.findByTahunTerbit", query = "SELECT b FROM Buku b WHERE b.tahunTerbit = :tahunTerbit"),
    @NamedQuery(name = "Buku.findByKontenDigital", query = "SELECT b FROM Buku b WHERE b.kontenDigital = :kontenDigital"),
    @NamedQuery(name = "Buku.findByTargetPembaca", query = "SELECT b FROM Buku b WHERE b.targetPembaca = :targetPembaca"),
    @NamedQuery(name = "Buku.findByBahasa", query = "SELECT b FROM Buku b WHERE b.bahasa = :bahasa"),
    @NamedQuery(name = "Buku.findByEdisi", query = "SELECT b FROM Buku b WHERE b.edisi = :edisi"),
    @NamedQuery(name = "Buku.findByDeskripsiFisik", query = "SELECT b FROM Buku b WHERE b.deskripsiFisik = :deskripsiFisik")})
public class Buku implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_buku")
    private String kodeBuku;
    @Basic(optional = false)
    @Column(name = "penulis_buku")
    private String penulisBuku;
    @Basic(optional = false)
    @Column(name = "judul_buku")
    private String judulBuku;
    @Basic(optional = false)
    @Column(name = "ISBN")
    private int isbn;
    @Basic(optional = false)
    @Column(name = "penerbit")
    private String penerbit;
    @Basic(optional = false)
    @Column(name = "eksemplar_buku")
    private int eksemplarBuku;
    @Basic(optional = false)
    @Column(name = "subjek")
    private String subjek;
    @Basic(optional = false)
    @Column(name = "tahun_terbit")
    private int tahunTerbit;
    @Basic(optional = false)
    @Column(name = "konten_digital")
    private String kontenDigital;
    @Basic(optional = false)
    @Column(name = "target_pembaca")
    private String targetPembaca;
    @Basic(optional = false)
    @Column(name = "bahasa")
    private String bahasa;
    @Basic(optional = false)
    @Column(name = "edisi")
    private int edisi;
    @Basic(optional = false)
    @Column(name = "deskripsi_fisik")
    private String deskripsiFisik;
    @JoinColumn(name = "ID_pustakawan", referencedColumnName = "ID_pustakawan")
    @ManyToOne(optional = false)
    private Pustakawan iDpustakawan;
    @JoinColumn(name = "nomor_rak", referencedColumnName = "nomor_rak")
    @ManyToOne(optional = false)
    private Rak nomorRak;

    public Buku() {
    }

    public Buku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public Buku(String kodeBuku, String penulisBuku, String judulBuku, int isbn, String penerbit, int eksemplarBuku, String subjek, int tahunTerbit, String kontenDigital, String targetPembaca, String bahasa, int edisi, String deskripsiFisik) {
        this.kodeBuku = kodeBuku;
        this.penulisBuku = penulisBuku;
        this.judulBuku = judulBuku;
        this.isbn = isbn;
        this.penerbit = penerbit;
        this.eksemplarBuku = eksemplarBuku;
        this.subjek = subjek;
        this.tahunTerbit = tahunTerbit;
        this.kontenDigital = kontenDigital;
        this.targetPembaca = targetPembaca;
        this.bahasa = bahasa;
        this.edisi = edisi;
        this.deskripsiFisik = deskripsiFisik;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public String getPenulisBuku() {
        return penulisBuku;
    }

    public void setPenulisBuku(String penulisBuku) {
        this.penulisBuku = penulisBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getEksemplarBuku() {
        return eksemplarBuku;
    }

    public void setEksemplarBuku(int eksemplarBuku) {
        this.eksemplarBuku = eksemplarBuku;
    }

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getKontenDigital() {
        return kontenDigital;
    }

    public void setKontenDigital(String kontenDigital) {
        this.kontenDigital = kontenDigital;
    }

    public String getTargetPembaca() {
        return targetPembaca;
    }

    public void setTargetPembaca(String targetPembaca) {
        this.targetPembaca = targetPembaca;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public int getEdisi() {
        return edisi;
    }

    public void setEdisi(int edisi) {
        this.edisi = edisi;
    }

    public String getDeskripsiFisik() {
        return deskripsiFisik;
    }

    public void setDeskripsiFisik(String deskripsiFisik) {
        this.deskripsiFisik = deskripsiFisik;
    }

    public Pustakawan getIDpustakawan() {
        return iDpustakawan;
    }

    public void setIDpustakawan(Pustakawan iDpustakawan) {
        this.iDpustakawan = iDpustakawan;
    }

    public Rak getNomorRak() {
        return nomorRak;
    }

    public void setNomorRak(Rak nomorRak) {
        this.nomorRak = nomorRak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeBuku != null ? kodeBuku.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buku)) {
            return false;
        }
        Buku other = (Buku) object;
        if ((this.kodeBuku == null && other.kodeBuku != null) || (this.kodeBuku != null && !this.kodeBuku.equals(other.kodeBuku))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kelompoknya.akmal.learnmigratedb.Buku[ kodeBuku=" + kodeBuku + " ]";
    }
    
}
