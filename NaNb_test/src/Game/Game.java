package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Game {
    private String answer;
    private JFrame frame;
    private JTextField guessField;
    private JTextArea resultArea;
    private int guessCount;
    //private JPanel topPanel;
    private JPanel topPanel = new JPanel(new GridLayout(0, 3));
    private JButton restartButton = new JButton("再來一次");;
    
    public Game() {
        this.answer = new RandomNum().ansNum();
        frame = new JFrame("幾A幾B遊戲");
        guessField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);
    }


	public void start() {
		guessCount = 0;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	restartButton.setEnabled(false);
        JPanel topLabel = new JPanel(new GridLayout(1, 1)); // 只有一个组件，使用GridLayout(1, 1)
        topLabel.add(new JLabel("請點擊數字："));

        //JPanel topPanel = new JPanel(new GridLayout(0, 3));
        //JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setPreferredSize(new Dimension(150,100));
        
        final int[] count = {0};  //記錄按了幾個數字
        for (int i = 1; i < 10; i++) {
            String imagePath ="img/" + i + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JButton numberButton = new JButton(new ImageIcon(img));
            numberButton.setActionCommand(String.valueOf(i));
            numberButton.setPreferredSize(new Dimension(100, 100));
            numberButton.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
                    String clickedNumber = e.getActionCommand();
                    topLabel.add(new JLabel(clickedNumber));
                    frame.add(topLabel, BorderLayout.NORTH); // 放在NORTH
                    frame.setVisible(true);
                    guessField.setText(guessField.getText() + clickedNumber);
                    count[0]++;
                    //改成超4清空
                    if(count[0] > 4) {
                    	count[0] = 0;
                    	//JOptionPane.showMessageDialog(frame, "Error: 请只输入四个数字！", "错误", JOptionPane.ERROR_MESSAGE);
                        // 清空文本框和数字数量
                        topLabel.removeAll(); // 移除所有元素
                        topLabel.add(new JLabel("請點擊數字:"));
                        frame.revalidate(); // 重新繪製視窗
                    }
                }
            });
            topPanel.add(numberButton);
        }
        
        
        
        JPanel guessPanel = new JPanel(new GridLayout(1,1));
        
        //restartButton = new JButton("再來一次");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 重新生成新的不重複數字，重置游戏
            	answer = new RandomNum().ansNum();
                resultArea.setText(""); // 清空结果区域
                guessField.setText(""); // 清空输入框
                enableNumberButtons(); // 启用数字按钮
            }
        });
        guessPanel.add(restartButton);
        
        JButton guessButton = new JButton("猜測");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	guessCount++;
            	count[0] = 0;//update count
                String guess = guessField.getText();
                String result = checkGuess(guess);
                resultArea.append(guess + ": " + result + "\n");
                if (result.equals("4A0B")) {
                    resultArea.append("恭喜你，你贏了！\n");
                    guessButton.setEnabled(false); // 游戏结束时禁用按钮
                    restartButton.setEnabled(true);
                }else if(guessCount > 10) {
                    resultArea.setText("失敗！正確答案為：" + answer);
                    guessButton.setEnabled(false); // 禁用猜测按钮
                }
                guessField.setText("");
                
                // 猜測後將topLabel的文本設為"請點擊數字:"
                topLabel.removeAll(); // 移除所有元素
                topLabel.add(new JLabel("請點擊數字:"));
                frame.revalidate(); // 重新繪製視窗
            }
        });
        guessPanel.add(guessButton);
        
        JButton deleteButton = new JButton("刪除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	count[0]--;
                // 删除最后一个输入的数字
                String currentText = guessField.getText();
                if (!currentText.isEmpty()) {
                    guessField.setText(currentText.substring(0, currentText.length() - 1));
                }
                
                Component[] components = topLabel.getComponents();
                if (components.length > 1) {
                    topLabel.remove(components[components.length - 1]);
                    topLabel.revalidate(); // 重新布局以确保 UI 刷新
                }
            }
        });
        guessPanel.add(deleteButton);

        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(guessPanel, BorderLayout.NORTH); // guessPanel放在NORTH
        resultArea.setPreferredSize(new Dimension(300, 200));
        bottomPanel.add(resultArea, BorderLayout.CENTER); // resultArea放在CENTER

        frame.add(topLabel, BorderLayout.NORTH);
        frame.add(topPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH); // bottomPanel放在SOUTH
        
        frame.setSize(400, 600);
        frame.setVisible(true);
    }




    private String checkGuess(String guess) {
        int aCount = 0;
        int bCount = 0;
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            if (answer.charAt(i) == guessChar) {
                aCount++;
            } else if (answer.contains(String.valueOf(guessChar))) {
                bCount++;
            }
        }
        return aCount + "A" + bCount + "B";
    }
    
 // 使数字按钮可用
    private void enableNumberButtons() {
        for (Component component : topPanel.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setEnabled(true);
            }
        }
    }
    

}