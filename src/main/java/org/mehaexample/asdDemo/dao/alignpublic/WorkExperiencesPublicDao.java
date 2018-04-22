package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignpublic.TopCoops;
import org.mehaexample.asdDemo.model.alignpublic.WorkExperiencesPublic;

import javax.persistence.TypedQuery;
import java.util.List;

public class WorkExperiencesPublicDao {
  private SessionFactory factory;

  public WorkExperiencesPublicDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    this.factory = PublicSessionFactory.getFactory();
  }

  public WorkExperiencesPublicDao(boolean test) {
    if (test) {
      this.factory = PublicTestSessionFactory.getFactory();
    }
  }

  /**
   * Create a work experience data in the coop table in the public
   * database.
   *
   * @param workExperience work experience data to be added.
   * @return work experience data if created.
   */
  public synchronized WorkExperiencesPublic createWorkExperience(WorkExperiencesPublic workExperience) {
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.save(workExperience);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }
    return workExperience;
  }

  /**
   * Find a work experience from the coop table in the public database
   * based on the work Experience Id.
   *
   * @param workExperienceId to be found from the database.
   * @return work experience object if found, null otherwise.
   */
  public WorkExperiencesPublic findWorkExperienceById(int workExperienceId) {
    List<WorkExperiencesPublic> list;
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(
              "FROM WorkExperiencesPublic WHERE workExperienceId = :workExperienceId ");
      query.setParameter("workExperienceId", workExperienceId);
      list = query.list();
    } finally {
      session.close();
    }
    if (list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  /**
   * Get top ten coop companies from the coop tables in the
   * public database.
   *
   * @param numberOfResultsDesired total number of results desired.
   * @return top ten coop companies.
   */
  public List<TopCoops> getTopCoops(int numberOfResultsDesired) {
    String hql = "SELECT NEW org.mehaexample.asdDemo.model.alignpublic.TopCoops(w.coop, Count(*)) " +
            "FROM WorkExperiencesPublic w " +
            "GROUP BY w.coop " +
            "ORDER BY Count(*) DESC ";
    List<TopCoops> listOfTopCoops;
    Session session = factory.openSession();
    try {
      TypedQuery<TopCoops> query = session.createQuery(hql, TopCoops.class);
      query.setMaxResults(numberOfResultsDesired);
      listOfTopCoops = query.getResultList();
    } finally {
      session.close();
    }
    return listOfTopCoops;
  }

  /**
   * Get list of all coop companies from the public database.
   *
   * @return list of all coop companies.
   */
  public List<String> getListOfAllCoopCompanies() {
    String hql = "SELECT w.coop " +
            "FROM WorkExperiencesPublic w " +
            "GROUP BY w.coop " +
            "ORDER BY Count(*) DESC";
    List<String> listOfAllCoopCompanies;
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(hql);
      listOfAllCoopCompanies = query.getResultList();
    } finally {
      session.close();
    }
    return listOfAllCoopCompanies;
  }

  /**
   * Delete Work Experience data in the coop table based on their
   * ID.
   *
   * @param workExperienceId work Exp Id to be deleted.
   * @return true if deleted.
   */
  public synchronized boolean deleteWorkExperienceById(int workExperienceId) {
    WorkExperiencesPublic workExperience = findWorkExperienceById(workExperienceId);
    if (workExperience != null) {
      Session session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(workExperience);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Work Experience Id does not exist.");
    }
    return true;
  }
}
