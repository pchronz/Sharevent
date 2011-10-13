package com.sharevent

import grails.test.*

class MainTests extends GroovyTestCase{

	def contentScaffolder
    
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testMainView() {
		MainController mainController = new MainController()
    }

    void testBootStrap(){
    	println "testBootStrap for ${contentScaffolder}"
        def imageCountBefore = Image.list().size()
    	assertTrue contentScaffolder.createContent()
        println "imageCount ${Image.list().size()}"
        assertEquals Image.list().size(), (10 + imageCountBefore)
    }
}
