// Place your Spring DSL code here
beans = {
	contentScaffolder(com.sharevent.ContentScaffolder){
		imageDBService = ref("imageDBService")
	}
}
