package com.farmexercise.Model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="fileobject")
public class FileObject extends AbstractPersistable<Long> {
    
    @Id
    @Lob
    @GeneratedValue(generator = "fileobject_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "fileobject_id_seq", sequenceName = "fileobject_id_seq")
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    private String tiedostonnimi;
    private String mediatyyppi;
    private Long tiedostonkoko;
    private String tallennettu;
    
    // Getteri
    public Long getId() {
        return id;
    }

    public byte[] getContent () {
        return content;
    }

    public String getTiedostonnimi () {
        return tiedostonnimi;
    }

    public String getMediatyyppi () {
        return mediatyyppi;
    }

    public Long getTiedostonkoko() {
        return tiedostonkoko;
    }

    public String getTallennettu () {
        return tallennettu;
    }

    // Setteri
    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setTiedostonnimi(String tiedostonnimi) {
        this.tiedostonnimi = tiedostonnimi;
    }

    public void setMediatyyppi(String mediatyyppi) {
        this.mediatyyppi = mediatyyppi;
    }

    public void setTiedostonkoko(Long tiedostonkoko) {
        this.tiedostonkoko = tiedostonkoko;
    }

    public void setTallennettu(String tallennettu) {
        this.tallennettu = tallennettu;
    }
}