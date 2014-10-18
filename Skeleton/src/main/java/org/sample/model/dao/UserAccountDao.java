/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.model.dao;

import org.sample.model.AddUserAccount;
import org.springframework.data.repository.CrudRepository;


public interface UserAccountDao extends CrudRepository<AddUserAccount,Long>{
    
}
