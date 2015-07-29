package test

import com.ticketbis.groobalize.Translation
import com.ticketbis.groobalize.ast.Field

class CompanyTranslation extends Translation<Company> {

  @Field(inherit=false)
  String title

  
  String synopsis = null // Inherit from parent locale
  

  static constraints = {
    synopsis nullable: true
  }
  
}
