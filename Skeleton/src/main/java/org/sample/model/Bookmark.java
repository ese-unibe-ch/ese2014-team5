package org.sample.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne
    private Advert ad;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Advert getAd() {
		return ad;
	}

	public void setAd(Advert ad) {
		this.ad = ad;
	}


}
