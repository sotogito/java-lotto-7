package lotto.domain.factory;

import static lotto.domain.InputErrorMessage.EMPTY_INPUT_VALUE;
import static lotto.domain.InputErrorMessage.LOTTO_INCORRECT_DELIMITER;
import static lotto.domain.InputErrorMessage.LOTTO_ONLY_NUMBER;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lotto.domain.lottos.Lotto;

public class UserMainLottoFactory {
    private final static String LOTTO_DELIMITER = ",";

    public Lotto make(String input) {
        String[] separatedInputValues = input.split(LOTTO_DELIMITER);
        List<Integer> lottoNumber = convertToNumbers(separatedInputValues);

        return new Lotto(lottoNumber);
    }

    private List<Integer> convertToNumbers(String[] strArray) {
        List<Integer> lottoNumber = new ArrayList<>();

        for (String value : strArray) {
            value = value.trim();
            validateContainSpecialCharacters(value);
            validateEmpty(value);

            lottoNumber.add(changeToNumber(value));
        }
        return lottoNumber;
    }

    private static int changeToNumber(String value) {
        int num;
        try {
            num = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LOTTO_ONLY_NUMBER.getMessage());
        }
        return num;
    }


    private static void validateContainSpecialCharacters(String value) {
        Matcher matcher = Pattern.compile("[!@#$%^&*().?\":{}|<>]").matcher(value);
        if (matcher.find()) {
            throw new IllegalArgumentException(LOTTO_INCORRECT_DELIMITER.getMessage());
        }
    }


    private static void validateEmpty(String value) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_INPUT_VALUE.getMessage());
        }
    }

}
