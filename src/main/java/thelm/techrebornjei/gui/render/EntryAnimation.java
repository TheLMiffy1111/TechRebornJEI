package thelm.techrebornjei.gui.render;

public record EntryAnimation(Direction direction, int duration) {

	public static final EntryAnimation UP = new EntryAnimation(Direction.UP, 5000);
	public static final EntryAnimation DOWN = new EntryAnimation(Direction.DOWN, 5000);
	public static final EntryAnimation STATIC = new EntryAnimation(Direction.STATIC, 0);

	public static EntryAnimation up(int duration) {
		return new EntryAnimation(Direction.UP, duration);
	}

	public static EntryAnimation down(int duration) {
		return new EntryAnimation(Direction.DOWN, duration);
	}

	public enum Direction {
		UP,
		DOWN,
		STATIC;
	}
}
