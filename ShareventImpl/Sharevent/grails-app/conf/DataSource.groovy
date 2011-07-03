dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
        }
    }
    test {
        dataSource {
			pooled = false
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/shareventProduction"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "grails"
			password = "grails"
        }
    }
    production {
        dataSource {
			pooled = false
            dbCreate = "update"
            url = "jdbc:mysql://shareventmain.cxzbjpiwkidf.us-east-1.rds.amazonaws.com:3306/shareventProduction"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "grails"
			password = "grails"
        }
    }
}
