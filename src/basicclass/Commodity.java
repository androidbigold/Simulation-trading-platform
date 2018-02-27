package basicclass;

public class Commodity {
	private String name = null;
	private int quantity = 0;
	private String belongto = null;
	private String signfrom = null;
	private String digitalsignature = null;

	public Commodity() {

	}

	public Commodity(String name, int quantity, String belongto,
			String signfrom, String digitalsignature) {
		this.name = name;
		this.quantity = quantity;
		this.belongto = belongto;
		this.signfrom = signfrom;
		this.digitalsignature = digitalsignature;
	}

	public void add() {
		quantity++;
	}

	public void addby(int n) {
		quantity += n;
	}

	public void reduce() {
		if (quantity != 0)
			quantity--;
		else
			System.out.println("商品数量为0，操作无效");
	}

	public void reduceby(int n) {
		if ((quantity - n) <= 0)
			quantity = 0;
		else
			quantity -= n;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getBelongto() {
		return belongto;
	}

	public String getSignfrom() {
		return signfrom;
	}

	public String getDigitalsignature() {
		return digitalsignature;
	}

	public void changeBelongto(String belongto) {
		this.belongto = belongto;
	}

	public void setDigitalsignature(String signfrom, String digitalsignature) {
		this.signfrom = signfrom;
		this.digitalsignature = digitalsignature;
	}

	public boolean equals(Object otherobject) {
		if (this == otherobject)
			return true;
		if (otherobject == null)
			return false;
		if (getClass() != otherobject.getClass())
			return false;
		Commodity other = (Commodity) otherobject;
		return name.equals(other.getName())&&quantity==other.getQuantity()
				&& belongto.equals(other.getBelongto())
				&& signfrom.equals(other.getSignfrom())
				&&digitalsignature.equals(other.getDigitalsignature());
	}
}