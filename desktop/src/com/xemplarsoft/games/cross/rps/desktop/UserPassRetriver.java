package com.xemplarsoft.games.cross.rps.desktop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.StandardCharsets;

import static com.xemplarsoft.utils.testdb.DataRequester.ACTION_LOGIN;

public class UserPassRetriver extends JFrame implements ActionListener {
    private JPanel content;
    private JLabel xgh;
    private JTextField username;
    private JButton signin;
    private JLabel user_label;
    private JLabel pass_label;
    private JPasswordField password;
    private final int action;

    private final DesktopLauncher launcher;
    public UserPassRetriver(DesktopLauncher launcher, int type){
        this.launcher = launcher;
        this.action = type;

        createUIComponents();
        try{
            this.setIconImage(ImageIO.read(new File("raw/app_icon_rounded.png")));
        } catch (Exception e){
            e.printStackTrace();
        }
        this.setTitle(DesktopLauncher.getAppName());

        Dimension d = new Dimension(300, 450);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);

        this.setContentPane(content);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void createUIComponents() {
        Font museo = null;
        try{
            File f = new File("fonts/museo.ttf");
            System.out.println("Font Exist: " + f.exists());
            System.out.println("Font Path: " + f.getAbsolutePath());
            museo = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(12F);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(museo);
        } catch (Exception e){
            e.printStackTrace();
            museo = getFont();
        }

        content = new JPanel();
        content.setLayout(new GridBagLayout());

        Dimension size = new Dimension(350, 48);
        xgh = new JLabel("Xemplar Gaming Hub");
        xgh.setMaximumSize(size);
        xgh.setMinimumSize(size);
        xgh.setPreferredSize(size);
        xgh.setHorizontalTextPosition(JLabel.CENTER);
        xgh.setHorizontalAlignment(JLabel.CENTER);
        xgh.setBackground(Color.black);
        xgh.setOpaque(true);
        xgh.setForeground(Color.white);
        xgh.setFont(museo.deriveFont(24F));
        content.add(xgh, getConstraints(0, 0, 1D, 0D, true, false));

        user_label = new JLabel(action == ACTION_LOGIN ? "Username:" : "New Username");
        user_label.setFont(museo);
        content.add(user_label, getConstraints(0, 1, 1D, 0D, true, true));

        username = new JTextField();
        username.setFont(museo);
        content.add(username, getConstraints(0, 2, 1D, 0D, true, true));

        pass_label = new JLabel(action == ACTION_LOGIN ? "Password:" : "New Password");
        pass_label.setFont(museo);
        content.add(pass_label, getConstraints(0, 3, 1D, 0D, true, true));

        password = new JPasswordField();
        content.add(password, getConstraints(0, 4, 1D, 0D, true, true));

        GridBagConstraints gbc_sep = new GridBagConstraints();
        gbc_sep.gridy = 5;
        gbc_sep.weightx = 1D;
        gbc_sep.weighty = 1D;
        gbc_sep.gridwidth = 2;
        gbc_sep.anchor = GridBagConstraints.NORTH | GridBagConstraints.CENTER;
        gbc_sep.insets = new Insets(5, 5, 0, 5);
        content.add(new JSeparator(JSeparator.HORIZONTAL), gbc_sep);

        signin = new JButton(action == ACTION_LOGIN ? "Login" : "Register");
        signin.addActionListener(this);
        signin.setFont(museo);
        GridBagConstraints gbc_email = getConstraints(0, 6, 1D, 0D, true, true);
        gbc_email.insets = new Insets(5, 5, 5, 5);
        content.add(signin, gbc_email);

        signin.addActionListener(this);
    }

    public GridBagConstraints getConstraints(int gridx, int gridy, double weightx, double weighty, boolean fill, boolean insets){
        GridBagConstraints ret = new GridBagConstraints();
        ret.fill = fill ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
        ret.gridx = gridx;
        ret.gridy = gridy;
        ret.weightx = weightx;
        ret.weighty = weighty;
        ret.gridwidth = 2;
        if(insets)
            ret.insets = new Insets(5, 5, 0, 5);
        return ret;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(signin)){
            byte[] pass = (new String(password.getPassword())).getBytes(StandardCharsets.UTF_8);
            setVisible(false);
            launcher.application.userPassReceived(username.getText(), pass, action);
        }
    }
}
