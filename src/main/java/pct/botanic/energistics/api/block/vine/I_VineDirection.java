
package pct.botanic.energistics.api.block.vine;

public interface I_VineDirection {
	
	public void setTarget(int[] target);
	
	public int[] getTarget();
	
	public byte toByte();
	
	public String toJSON();

	void invert();

}
