package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopGradYears;

import javax.persistence.TypedQuery;
import java.util.*;

public class StudentsPublicDao {
  private SessionFactory factory;

  public StudentsPublicDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    this.factory = PublicSessionFactory.getFactory();
  }

  public StudentsPublicDao(boolean test) {
    if (test) {
      this.factory = PublicTestSessionFactory.getFactory();
    }
  }

  /**
   * Create a Students row in the database for the student table in
   * public database.
   *
   * @param student student object that wants to be created.
   * @return student object if successfully created.
   */
  public synchronized StudentsPublic createStudent(StudentsPublic student) {
    Transaction tx = null;
    if (findStudentByPublicId(student.getPublicId()) != null) {
      throw new HibernateException("Student already exist in public database.");
    } else {
      Session session = factory.openSession();
      try {
        tx = session.beginTransaction();
        session.save(student);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    }
    return student;
  }

  /**
   * Find a student in public database by their public id.
   *
   * @param publicId the public id of a student to be searched.
   * @return found students if exist, null otherwise.
   */
  public StudentsPublic findStudentByPublicId(int publicId) {
    List<StudentsPublic> list;
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery("FROM StudentsPublic WHERE publicId = :publicId ");
      query.setParameter("publicId", publicId);
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
   * Get list of count of students based on their graduation years.
   * Sorted based on the total count of the students (Descending).
   *
   * @param numberOfResultsDesired total number of results.
   * @return list of count of students based on their grad years.
   */
  public List<TopGradYears> getTopGraduationYears(int numberOfResultsDesired) {
    String hql = "SELECT NEW org.mehaexample.asdDemo.model.alignpublic.TopGradYears(s.graduationYear, Count(*)) " +
            "FROM StudentsPublic s " +
            "GROUP BY s.graduationYear " +
            "ORDER BY Count(*) DESC ";
    List<TopGradYears> listOfTopGradYears;
    Session session = factory.openSession();
    try {
      TypedQuery<TopGradYears> query = session.createQuery(hql, TopGradYears.class);
      query.setMaxResults(numberOfResultsDesired);
      listOfTopGradYears = query.getResultList();
    } finally {
      session.close();
    }
    return listOfTopGradYears;
  }

  /**
   * Get list of all Students' graduation years.
   *
   * @return list of Integer for Students' grad years.
   */
  public List<Integer> getListOfAllGraduationYears() {
    List<Integer> listOfAllGraduationYears;
    Session session = factory.openSession();
    try {
      String hql = "SELECT s.graduationYear " +
              "FROM StudentsPublic s " +
              "GROUP BY s.graduationYear " +
              "ORDER BY s.graduationYear ASC";
      org.hibernate.query.Query query = session.createQuery(hql);
      listOfAllGraduationYears = query.getResultList();
    } finally {
      session.close();
    }
    return listOfAllGraduationYears;
  }

  /**
   * Get list of all students in Northeastern University.
   *
   * @return list of all students.
   */
  public List<StudentsPublic> getListOfAllStudents() {
    List<StudentsPublic> listOfAllStudents;
    Session session = factory.openSession();
    try {
      String hql = "SELECT s " +
              "FROM StudentsPublic s " +
              "WHERE s.visibleToPublic = true " +
              "ORDER BY s.graduationYear DESC";
      org.hibernate.query.Query query = session.createQuery(hql);
      listOfAllStudents = query.getResultList();
    } finally {
      session.close();
    }
    return listOfAllStudents;
  }

  /**
   * Get list of students based on the filtering of the user.
   *
   * @param filters searching filter.
   * @param begin   begin index.
   * @param end     end index
   * @return return the list of students based on the filter and the pagination index.
   */
  public List<StudentsPublic> getPublicFilteredStudents(Map<String, List<String>> filters, int begin, int end) {
    StringBuilder hql = new StringBuilder("SELECT Distinct(s) " +
            "FROM StudentsPublic s " +
            "LEFT OUTER JOIN WorkExperiencesPublic we ON s.publicId = we.publicId " +
            "LEFT OUTER JOIN UndergraduatesPublic u ON s.publicId = u.publicId " +
            "WHERE (s.visibleToPublic = true) ");
    return (List<StudentsPublic>) populatePublicFilteredHql(hql, filters, begin, end);
  }

  /**
   * Get the count of results based on the User's filtering.
   *
   * @param filters user's search filter.
   * @return count of results based on the filter.
   */
  public int getPublicFilteredStudentsCount(Map<String, List<String>> filters) {
    StringBuilder hql = new StringBuilder("SELECT Count ( Distinct s ) " +
            "FROM StudentsPublic s " +
            "LEFT OUTER JOIN WorkExperiencesPublic we ON s.publicId = we.publicId " +
            "LEFT OUTER JOIN UndergraduatesPublic u ON s.publicId = u.publicId " +
            "WHERE (s.visibleToPublic = true) ");
    List<Long> count = (List<Long>) populatePublicFilteredHql(hql, filters, null, null);
    return count.get(0).intValue();
  }

  /**
   * Helper class for populating the hibernate query language.
   *
   * @param hql     the String of Hibernate Query.
   * @param filters User's filter.
   * @param begin   begin index.
   * @param end     end index.
   * @return results based on the User's filters and hibernate query.
   */
  private List populatePublicFilteredHql(StringBuilder hql, Map<String, List<String>> filters, Integer begin, Integer end) {
    Set<String> filterKeys = filters.keySet();
    for (String filter : filterKeys) {
      hql.append("AND ");
      hql.append("(");
      boolean first = true;
      List<String> filterElements = filters.get(filter);
      for (int i = 0; i < filterElements.size(); i++) {
        if (!first) {
          hql.append(" OR ");
        }
        if (first) {
          first = false;
        }
        if (filter.equals("coop")) {
          hql.append("we.").append(filter).append(" = :").append(filter).append(i);
        } else if (filter.equals("graduationYear")) {
          hql.append("s.").append(filter).append(" = :").append(filter).append(i);
        } else {
          hql.append("u.").append(filter).append(" = :").append(filter).append(i);
        }
      }
      hql.append(") ");
    }

    hql.append(" ORDER BY s.graduationYear DESC ");
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(hql.toString());
      if (begin != null || end != null) {
        query.setFirstResult(begin - 1);
        query.setMaxResults(end - begin + 1);
      }
      for (String filter : filterKeys) {
        List<String> filterElements = filters.get(filter);
        for (int i = 0; i < filterElements.size(); i++) {
          if (filter.equals("graduationYear")) {
            query.setParameter(filter + i, Integer.parseInt(filterElements.get(i)));
          } else {
            query.setParameter(filter + i, filterElements.get(i));
          }
        }
      }
      return query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Update a student information in the public database.
   *
   * @param student updated student information.
   * @return true if updated.
   */
  public synchronized boolean updateStudent(StudentsPublic student) {
    if (findStudentByPublicId(student.getPublicId()) == null) {
      throw new HibernateException("Cannot find student with that public Id");
    }

    Transaction tx = null;

    Session session = factory.openSession();
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(student);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return true;
  }

  /**
   * Delete a student by their public Id.
   *
   * @param publicId public Id to be deleted.
   * @return true if deleted.
   */
  public synchronized boolean deleteStudentByPublicId(int publicId) {
    StudentsPublic student = findStudentByPublicId(publicId);
    if (student != null) {
      Session session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(student);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Public Id does not exist.");
    }

    return true;
  }
}
