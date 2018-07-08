package br.com.livingrio.converters;

public interface IConverter<T,K> {
	
	
	public K toDTO (T arg);
	
	public T toReal (K arg);
	

}
