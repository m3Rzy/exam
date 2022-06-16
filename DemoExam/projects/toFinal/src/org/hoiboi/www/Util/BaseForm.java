package org.hoiboi.www.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    public static String APP_TITLE = "О, этто мнэ!";
    public static Image APP_ICON = null;
    static
    {
        try {
            APP_ICON = ImageIO.read(BaseForm.class.getClassLoader().getResource("gg.jpg"));
        } catch (IOException e) {
        }
    }
    public BaseForm(int width, int height)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height /2
        );
        setTitle(APP_TITLE);

        if(APP_ICON != null)
        {
            setIconImage(APP_ICON);
        } else
        {
            DialogUtil.showError(this, "Ошибка отображения логотипа!");
            return;
        }
    }
}
