package test2

import grails.plugin.springsecurity.acl.AclClass
import grails.plugin.springsecurity.acl.AclService
import grails.plugin.springsecurity.acl.AclUtilService
import grails.plugin.springsecurity.annotation.Secured

import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.model.ObjectIdentity

import test.Company
import test.CompanyService
import test.User

 
import org.hibernate.SessionFactory
import org.hibernatespatial.GeometryUserType
import org.hibernatespatial.criterion.SpatialRestrictions

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.io.WKTReader


@Secured("ROLE_USER")
class HomeController {

  def springSecurityService
  AclService aclService
  AclUtilService aclUtilService
  CompanyService companyService
  def objectIdentityRetrievalStrategy
  
  GeometryFactory geometryFactory = new GeometryFactory()
  
  SessionFactory sessionFactory
  
 
    def index() { 
      println("test index")
      
      
//      Book book = new Book()
//      book.title = "test2"
//      if (!book.save(flush: true)) {
//        println "errors: "+book.errors
//      }
      
//      Book book = Book.get(3)
//      
//      println "book: "+book

      // 53.562231, 9.977312
      
//      book.location = geometryFactory.createPoint(new Coordinate(5d, 5d))
//      book.save(flush: true)
//        
//      Book book2 = Book.get(2)
//      
//      println("book.location: "+book2.location)
      
/*      
      // HQL
      
//      53.548931, 9.993614
      
      Coordinate cord = new Coordinate(53.548931, 9.993614)
     
      WKTReader reader = new WKTReader() 
      
      // find all in the poligon
      
//      Book.withNewTransaction {
      
        println(Book.createCriteria()
          .add(SpatialRestrictions.within("location", reader.read('POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))')))
          .list())
//      
//      def filter = reader.read('POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))')
//      def q = Book.createQuery("from Book where within(location, ?) = true")
//////      println "q: "+q.class
//      q.setParameter(0, filter, GeometryUserType.TYPE)
////      println (q.list())
        
        // HQL
        
        def filter = reader.read('POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))')
      
        def session2 = sessionFactory.currentSession
        def q = session2.createQuery("from Book b where within(location, ?) = true")
        q.setParameter(0, filter, GeometryUserType.TYPE)
        println (q.list())
        
       
        println("filter: "+filter)
        
 */
       
    }
  
  
  def createCompany1() {
    
    User user = springSecurityService.currentUser
    println ("user: "+user)
    
    
    if (!Company.findByName("company1")) {
      def company = new Company(name: "company1")
      company.save(flush:true)
      companyService.addPermission( company, springSecurityService.currentUser.username, BasePermission.ADMINISTRATION)
    }
    
    render "company1 created"
  }
  
  
  def createCompany2() {
    
    ObjectIdentityImpl s
    
    if (!Company.findByName("company2")) {
      def company = new Company(name: "company2")
      company.save(flush:true)
      companyService.addPermission( company, springSecurityService.currentUser.username, BasePermission.ADMINISTRATION)
    }
    
    render "company2 created"
  }
  
  def addPerm() {
    
    User user = springSecurityService.currentUser
    println ("user: "+user)
    
    Company company = Company.list().last()
    println("company: "+company)
    
    if (AclClass.findByClassName(Company.class.name)) {
      println "ja"
      def objIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(company)      
      println "objIdentity: "+objIdentity
      
//      objectIdentityRetrievalStrategy.createObjectIdentity(objIdentity, Company.class.name)
      ObjectIdentity oid = objectIdentityRetrievalStrategy.createObjectIdentity(objIdentity.getIdentifier(), Company.class.name+"ss")
      
//      def acl = aclUtilService.readAcl(company)
    }
    
    
    // Create the acl_object_identity row
//    createObjectIdentity(objectIdentity, sid);
    
//    companyService.addPermission( company, springSecurityService.currentUser.username, BasePermission.ADMINISTRATION)
    
    render "addPerm"
  }
  
  
  def removePerm() {
    User user = springSecurityService.currentUser
    println ("user: "+user)
    
    Company company = Company.list().last()
    println("company: "+company)
    
    companyService.deletePermission( company, springSecurityService.currentUser.username, BasePermission.ADMINISTRATION)
  
    render "removePerm"
 }
  
  
  def list() {
    
    def companies = companyService.list()
    println "companies "+companies
    println "companies size "+companyService.list().size()
    
    render "list: "+companies
  }
  
}
