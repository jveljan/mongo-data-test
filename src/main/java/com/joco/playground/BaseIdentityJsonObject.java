package com.joco.playground;




import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.Id;

public class BaseIdentityJsonObject extends BaseToJsonStringObject {
	
	@Id
	private ObjectId id;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
}
