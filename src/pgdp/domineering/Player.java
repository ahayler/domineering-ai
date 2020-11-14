package pgdp.domineering;

public enum Player {
	H, V;

	public Player getOtherPlayer() {
		return this == H ? V : H;
	}
}
