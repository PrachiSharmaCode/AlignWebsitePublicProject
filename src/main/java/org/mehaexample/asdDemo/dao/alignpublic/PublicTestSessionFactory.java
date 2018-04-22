package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PublicTestSessionFactory {
  private static SessionFactory factory;

  /**
   * Search for specific test hibernate configuration to connect to the
   * test database.
   */
  static {
    factory = new Configuration()
            .configure("/hibernate_public_test.cfg.xml").buildSessionFactory();
  }

  /**
   * Return the back-end database session factory connection.
   *
   * @return back-end database session factory.
   */
  public synchronized static SessionFactory getFactory() {
    return factory;
  }
}
