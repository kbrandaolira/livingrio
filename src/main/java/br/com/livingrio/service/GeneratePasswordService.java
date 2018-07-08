package br.com.livingrio.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GeneratePasswordService implements IService<Object> {

	public ServiceResponse execute(Object notNecessary) {

		return new ServiceResponse(true, randomString(6));
	}

	private static final Random random = new java.util.Random();

	private static char toChar(int index) {
		if (index < 10)
			return (char) (index + 48);// 48 a 57 -> [0 - 9]
		else if (index < 36)
			return (char) (index + 55);// 65 a 90 -> [A - Z]
		else
			return (char) (index + 61);// 97 a 122 -> [a - z]
	}

	private static char randomChar() {
		return toChar(random.nextInt(62));
	}

	private static String randomString(int length) {
		return new String(randomCharSequence(length));
	}

	private static char[] randomCharSequence(int length) {
		if (length < 0)
			throw new IllegalArgumentException(
					"O tamanho da String não pode ser negativo");
		if (length == 0)
			return new char[0];

		char[] charSequence = new char[length];
		for (int i = 0; i < length; i++) {
			charSequence[i] = randomChar();
		}

		return charSequence;
	}

}
