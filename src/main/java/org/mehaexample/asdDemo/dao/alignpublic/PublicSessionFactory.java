package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PublicSessionFactory {
  private static SessionFactory factory;

  /**
   * Search for the specific config file for
   * hibernate to connect to the database.
   */
  static {
    factory = new Configuration()
            .configure("/hibernate_Public.cfg.xml").buildSessionFactory();
  }

  /**
   * Return the session factory connection to the database.
   *
   * @return back-end database session factory.
   */
  public synchronized static SessionFactory getFactory() {
    return factory;
  }
}
