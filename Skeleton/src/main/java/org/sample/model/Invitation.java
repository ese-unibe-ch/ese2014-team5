/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Anubis
 */
@Entity
public class Invitation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Advert advert;

    @OneToOne
    private User userFrom;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<User> users;

    private Date fromDate;
    private Date toDate;

    private boolean invited;

    private String textOfInvitation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(User user) {

        if (users == null) {
            users = new HashSet<User>();

        }

        this.users.add(user);
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean getInvited() {
        return invited;
    }

    public void setInvited(boolean invited) {
        this.invited = invited;
    }

    public String getTextOfInvitation() {
        return textOfInvitation;
    }

    public void setTextOfInvitation(String textOfInvitation) {
        this.textOfInvitation = textOfInvitation;
    }

}
