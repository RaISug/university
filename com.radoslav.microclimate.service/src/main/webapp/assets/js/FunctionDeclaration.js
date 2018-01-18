(function() {
	if(!Object.keys) {
		Object.keys = function(object){
		    if (object !== Object(object)) {
		    	throw new TypeError('Object.keys called on non-object');
		    }
	
		    var keys = []
		    for(var key in object) {
		    	if(Object.prototype.hasOwnProperty.call(object,key)) {
		    		keys.push(key);
		    	}
		    }
		    return keys;
		}
	}
	
	if (!String.format) {
		String.format = function(format) {
			var formatedString = format;
			var formatArgs = Array.prototype.slice.call(arguments, 1);
			for (var i = 0; i < formatArgs.length ; i++) {
				formatedString = formatedString.replace(/{(\w+)}/, formatArgs[i]);
			}
			return formatedString;
		};
	}
	
	if (!Object.isEmpty) {
		Object.isEmpty = function(object) {
			if (object == null) {
				return true;
			}
			
			if (object.length === 0) {
				return true;
			}
			
			for (var key in object) {
				if (Object.prototype.hasOwnProperty.call(object, key)) {
					return false;
				}
			}
			
			return true;
		}
	}
})();