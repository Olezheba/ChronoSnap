package com.example.chronosnap.utils;

import static android.provider.Settings.System.getString;

import android.content.Context;

import com.example.chronosnap.R;

public class CategoryUtils {

    Context context;

    public static final int[] CATEGORY_COLORS = {
            0xFF5C6BC0, // Индиго
            0xFF26A69A, // Бирюзовый
            0xFF66BB6A, // Зелёный
            0xFFD4E157, // Лаймовый
            0xFFFFCA28, // Жёлтый
            0xFFFFA726, // Оранжевый
            0xFFEC407A, // Розовый
            0xFF7E57C2, // Лавандовый
            0xFF26C6DA, // Светло-бирюзовый
            0xFFFFEE58, // Светло-жёлтый
            0xFFFF7043, // Персиковый
            0xFF78909C, // Сине-серый
            0xFF8E24AA, // Глубокий фиолетовый
            0xFF039BE5, // Глубокий голубой
            0xFF00897B, // Морской зелёный
            0xFF43A047  // Насыт. зелёный
    };

    public static final int[] APP_CATEGORY_COLORS = {
            0xFFBDBDBD, // Серый
            0xFFEF5350, // Красный
            0xFF29B6F6, // Голубой
            0xFF42A5F5, // Синий
            0xFF66BB6A, // Ярко-зелёный
            0xFFAB47BC, // Фиолетовый
            0xFF8D6E63, // Коричневый
            0xFFA1887F, // Серо-коричневый
            0xFF3949AB, // Глубокий индиго
    };

    public String[] getApplicationCategories() {
        return new String[] {
                context.getString(R.string.undefined),
                context.getString(R.string.game),
                context.getString(R.string.audio),
                context.getString(R.string.video),
                context.getString(R.string.image),
                context.getString(R.string.social),
                context.getString(R.string.news),
                context.getString(R.string.maps),
                context.getString(R.string.productivity)
        };
    }

    public CategoryUtils(Context context) {
        this.context = context;
    }
}
