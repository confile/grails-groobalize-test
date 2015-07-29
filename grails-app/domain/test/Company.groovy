package test

import com.ticketbis.groobalize.ast.Translatable


@Translatable(with = CompanyTranslation)
class Company {

  String name
  
    static constraints = {
    }
}
