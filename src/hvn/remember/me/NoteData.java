package hvn.remember.me;

public class NoteData {

	public NoteData(int order, String content) {
		this.content = content;
		this.order = order;
	}

	public NoteData() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	int order;
	String content;
	int id;

	@Override
	public String toString() {
		return "NoteData [order=" + order + ", content=" + content + ", id="
				+ id + "]";
	}

}
