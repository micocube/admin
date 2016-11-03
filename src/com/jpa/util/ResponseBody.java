package com.jpa.util;

import java.util.List;

public class ResponseBody {
		private Integer success;
		private String editorMode;
		private String message;
		private String content;
		private List<?> entities;
		
		public ResponseBody(Integer success,String editorMode, String message, String content) {
			super();
			this.success  = success;
			this.editorMode = editorMode;
			this.message =DateUtils.get(null,null)+" "+ message;
			this.content = content;
		}
		
		
		public ResponseBody(String message) {
			super();
			this.message =DateUtils.get(null,null)+" "+ message;
		}


		public ResponseBody(String message, List<?> entities) {
			super();
			this.message = DateUtils.get(null,null)+" "+ message;
			this.entities = entities;
		}


		public List<?> getEntities() {
			return entities;
		}


		public void setEntities(List<?> entities) {
			this.entities = entities;
		}


		public String getEditorMode() {
			return editorMode;
		}
		public void setEditorMode(String editorMode) {
			this.editorMode = editorMode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}


		public Integer getSuccess() {
			return success;
		}


		public void setSuccess(Integer success) {
			this.success = success;
		}
		
		
		
}
