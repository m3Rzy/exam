package org.hoiboi.ww.Util;

import javax.swing.*;
import java.awt.*;

public class DialogUtil extends JFrame {
    public static void showError(Component component, String text)
    {
        JOptionPane.showMessageDialog(component, text, " Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component component, String text)
    {
        JOptionPane.showMessageDialog(component, text, " Информация", JOptionPane.INFORMATION_MESSAGE);
    }
}
