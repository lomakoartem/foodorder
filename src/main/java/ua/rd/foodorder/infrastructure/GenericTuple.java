package ua.rd.foodorder.infrastructure;

public class GenericTuple<T, S> {

	private final T first;
	
	private final S second;
	
	public GenericTuple(T first, S second) {
		super();
		this.first = first;
		this.second = second;
	}
	public T getFirst() {
		return first;
	}
	public S getSecond() {
		return second;
	}
	
}
