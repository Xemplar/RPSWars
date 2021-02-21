package com.xemplarsoft.games.cross.rps.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.lwjgl.Sys;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;

public class EmailRetriever extends JFrame implements ActionListener {
    private JPanel content;
    private JLabel xgh;
    private JTextField email_field;
    private JButton policyX;
    private JCheckBox agree_coppa;
    private JButton checkEmail;
    private JCheckBox agree_xemplar;

    private DesktopLauncher launcher;
    public EmailRetriever(DesktopLauncher launcher){
        this.launcher = launcher;

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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
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

        JLabel email_label = new JLabel("Email: ");
        email_label.setFont(museo);
        content.add(email_label, getConstraints(0, 1, 1D, 0D, true, true));

        email_field = new JTextField();
        email_field.setFont(museo);
        content.add(email_field, getConstraints(0, 2, 1D, 0D, true, true));

        JLabel disc = new JLabel("<html><div style='text-align:justify'>By logging into our site or app, or providing your email, you agree our privacy policy." +
                " Continued use of our website or app, is a continued agreement by you, the user, to our" +
                " policy. Click on the buttons below to view our policy in your browser. I also acknowledge that if I am under" +
                " the age of 13, I must ask a parent or guardian before playing online, registering an account, or submitting my" +
                " email.</div></html>");
        disc.setFont(museo);
        content.add(disc, getConstraints(0, 3, 1D, 0D, true, true));

        policyX = new JButton("Xemplar's Policy");
        policyX.addActionListener(this);
        policyX.setFont(museo);
        content.add(policyX, getConstraints(0, 4, 1D, 0D, true, true));

        agree_coppa = new JCheckBox();
        content.add(agree_coppa, getCBConstraints(5));
        content.add(new JLabel("I agree to the above statement."), getCBLabelConstraints(5));
        agree_xemplar = new JCheckBox();
        content.add(agree_xemplar, getCBConstraints(6));
        content.add(new JLabel("I agree to Xemplar's privacy policy."), getCBLabelConstraints(6));

        GridBagConstraints gbc_sep = new GridBagConstraints();
        gbc_sep.gridy = 7;
        gbc_sep.weightx = 1D;
        gbc_sep.weighty = 1D;
        gbc_sep.gridwidth = 2;
        gbc_sep.anchor = GridBagConstraints.NORTH | GridBagConstraints.CENTER;
        gbc_sep.insets = new Insets(5, 5, 0, 5);
        content.add(new JSeparator(JSeparator.HORIZONTAL), gbc_sep);

        checkEmail = new JButton("Check Email");
        checkEmail.addActionListener(this);
        checkEmail.setFont(museo);
        GridBagConstraints gbc_email = getConstraints(0, 8, 1D, 0D, true, true);
        gbc_email.insets = new Insets(5, 5, 5, 5);
        content.add(checkEmail, gbc_email);

        checkEmail.addActionListener(this);
        content.setBackground(Color.white);
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

    public GridBagConstraints getCBConstraints(int gridy){
        GridBagConstraints ret = new GridBagConstraints();
        ret.fill = GridBagConstraints.BOTH;
        ret.gridx = 0;
        ret.gridy = gridy;
        ret.gridwidth = 1;
        ret.insets = new Insets(5, 5, 0, 5);
        return ret;
    }

    public GridBagConstraints getCBLabelConstraints(int gridy){
        GridBagConstraints ret = new GridBagConstraints();
        ret.fill = GridBagConstraints.BOTH;
        ret.gridx = 1;
        ret.gridy = gridy;
        ret.weightx = 1D;
        ret.gridwidth = 1;
        ret.insets = new Insets(5, 5, 0, 0);
        return ret;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(checkEmail)){
            launcher.application.emailReceived(email_field.getText());
            this.setVisible(false);
        } else if(e.getSource().equals(policyX)){
            Desktop desktop = java.awt.Desktop.getDesktop();
            try {
                URI oURL = new URI("https://xemplarsoft.com/testdb/chess_policy");
                desktop.browse(oURL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
