package subaraki.paintings.fake_enum;

import java.util.HashMap;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Painting {

	public static HashMap<String, Painting> paintings = new HashMap<String, Painting>();
	
	private int x,y,u,v;
	private String name;
	
	public Painting(int x, int y , int u , int v, String name) {
		this.x=x;
		this.y=y;
		this.u=u;
		this.v=v;
		this.name=name;
	}
	
	public int getU() {
		return u;
	}
	
	public int getV() {
		return v;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public String getName() {
		return name;
	}
	
	public Painting enlist() {
		paintings.put(getName(), this);
		return this;
	}
	
	public void toBuffer(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(u);
		buf.writeInt(v);
		ByteBufUtils.writeUTF8String(buf, name);
	}
	
	public static Painting fromByteBuffer(ByteBuf buf) {
		return new Painting(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), ByteBufUtils.readUTF8String(buf));
	}
}
