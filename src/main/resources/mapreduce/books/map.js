function() {
	var key = this.pages > 200 ? 'Big Books' : 'Small Books';
	emit(key, {name: this.name});
}