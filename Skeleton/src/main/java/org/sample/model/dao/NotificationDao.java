package org.sample.model.dao;

import org.sample.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDao  extends JpaRepository<Notification,Long>
{

}
