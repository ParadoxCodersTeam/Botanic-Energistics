package pct.botanic.energistics.blocks.vine;

import pct.botanic.energistics.api.block.vine.I_VineDirection;


public class VineDirection implements I_VineDirection {
	
	protected int[] target = {0,0,0};
	
	public VineDirection(int xOffset, int yOffset, int zOffset) {
		int[] newTarget = {xOffset,yOffset,zOffset};
		this.setTarget(newTarget);
	}
	
	//TODO: Solve why connections of Y= 1, X&Z=0 evaluate to -89 instead of 111.
	
	public VineDirection(byte b) {
		if(b>26){
			throw new IllegalArgumentException("Direction Byte too Big: "+b);
		}
		target[0]=(b%3)-1;
		target[1]=((b/3)%3)-1;
		target[2]=(b/9)-1;
		
	}

	@Override
	public void setTarget(int[] targetDir) {
		
		if(targetDir[0]>1 || targetDir[0] <-1)
			throw new IllegalArgumentException("X Offset Invalid: "+targetDir[0]);
		if(targetDir[1]>1 || targetDir[1] <-1)
			throw new IllegalArgumentException("X Offset Invalid: "+targetDir[1]);
		if(targetDir[2]>1 || targetDir[2] <-1)
			throw new IllegalArgumentException("X Offset Invalid: "+targetDir[0]);
		int[] newTarget = {targetDir[0],targetDir[1],targetDir[2]};
		this.target = newTarget;
		
	}
	
	@Override
	public int[] getTarget() {
		int[] response = {target[0],target[1],target[2]};
		return response;
	}

	@Override
	public byte toByte() {
		
		return (byte)((target[0])+(target[1]*3)+(target[2]*9));
	}

	@Override
	public String toJSON() {
		String result = "\ntarget{";
		result+=("\n\t\"x\":\""+(target[0])+"\",");
		result+=("\n\t\"y\":\""+(target[1])+"\",");
		result+=("\n\t\"z\":\""+(target[2])+"\"");
		result+="\n}\n";
		return result;
	}
	
	@Override
	public String toString() {
		String result = "\ntarget:";
		result+=("\n\t\"x:"+(target[0]));
		result+=("\n\t\"y:"+(target[1]));
		result+=("\n\t\"z:"+(target[2])+"\n");
		return result;
	}
	
	@Override
	public void invert() {
		int[] targetArr = this.getTarget();
		for(int i = 0; i<3;i++){
			switch(targetArr[i]){
			case -1: targetArr[i]=1; break;
			case 1: targetArr[i]=-1; break;
			default: break;
			}
		}
		this.setTarget(targetArr);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null||!(obj instanceof I_VineDirection))
			return false;
		return ((I_VineDirection)obj).toByte()==this.toByte();
	}
}
