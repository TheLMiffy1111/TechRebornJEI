package thelm.techrebornjei;

public record EntryAnimation(Type type, int duration) {

	public static final EntryAnimation UPWARDS = new EntryAnimation(Type.UPWARDS, 5000);
	public static final EntryAnimation DOWNWARDS = new EntryAnimation(Type.DOWNWARDS, 5000);
	public static final EntryAnimation NONE = new EntryAnimation(Type.NONE, 0);

	public static EntryAnimation upwards(int duration) {
		return new EntryAnimation(Type.UPWARDS, duration);
	}

	public static EntryAnimation downwards(int duration) {
		return new EntryAnimation(Type.DOWNWARDS, duration);
	}

	public enum Type {
		UPWARDS,
		DOWNWARDS,
		NONE;
	}
}
