//package GameCode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Frame extends JFrame{

	public static void main(String args[]){
		new Frame();
	}

	GameExtra g;
	Label board[][];

	public Frame(){
		this.setVisible(true);
		this.setTitle("2048 Game");
		this.setSize(360, 440);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {System.exit(0);}
				//�رմ���ʱ���˳����л�������������
			
		});

		//������ʼ����Ϸ�İ�ť
		JButton bNewGame=new JButton();
		bNewGame.setText("NewGAme");
		bNewGame.setBounds(130, 320, 100, 50);
		bNewGame.setFocusable(false);
		bNewGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.Init();
			}
		});
		this.add(bNewGame);

		//����������ʾ�������label
		board=new Label[4][4];
		for(int i=0;i<16;i++){
			int x=(i/4),y=(i%4);
			Label t=new Label("0",Label.CENTER);
			board[x][y]=t;
			t.setSize(60, 60);
			t.setLocation(50+x*65, 50+(3-y)*65);
			t.setBackground(Color.PINK);
			t.setFont(new Font("Serif",1,20));
			t.setText(String.valueOf(x*10+y));
			this.add(t);
		}



		//ʵ������Ϸ�߼�����
		g=new GameExtra(this);
		this.addKeyListener(g);
		
	}

	public void ChangeBoard(int x,int y,int d){
		//�˺������ڵ���label�ڸ��ֲ�ͬ����ʱ����ʾЧ��
		Color c;
		int FontSize;
		switch(d){
		case 0:
			c=new Color(204,192,178);
			FontSize=20;
			break;
		case 2:
			c=new Color(237,227,218);
			FontSize=30;
			break;
		case 4:
			c=new Color(243,150,99);
			FontSize=30;
			break;
		case 8:
			c=new Color(246,147,96);
			FontSize=30;
			break;
		case 16:
			c=new Color(247,148,97);
			FontSize=27;
			break;
		case 32:
			c=new Color(250,122,91);
			FontSize=27;
			break;
		case 64:
			c=new Color(245,93,61);
			FontSize=27;
			break;
		case 128:
			c=new Color(240,206,116);
			FontSize=24;
			break;
		case 256:
			c=new Color(237,203,101);
			FontSize=24;
			break;
		case 512:
			c=new Color(238,197,79);
			FontSize=24;
			break;
		case 1024:
			c=new Color(238,199,68);
			FontSize=20;
			break;
		case 2048:
			c=new Color(238,194,46);
			FontSize=20;
			break;
		case 4096:
			
		
		case 8192:
			

		default:
			c=new Color(253,61,58);
			FontSize=20;
			break;

		}

		
		board[x][y].setBackground(c);							//���ö�Ӧ�ı�����ɫ
		board[x][y].setText(d==0?" ":String.valueOf(d));		//���÷�����Ҫ��ʾ������
		board[x][y].setFont(new Font("�����п�",1,FontSize));	//����������ʾ��Ч���Լ����������ʵĴ�С
		board[x][y].setForeground(d<=4?Color.gray:Color.WHITE);	//�����������ɫ
	}

}


final class GameExtra extends Game{

	GameExtra(Frame f){
		//���췽��������ϷGUIʵ�����룬���ڿ��ƣ������Ի���Ϸ
		this.f=f;
		Init();
	};	
	Frame f;

	@Override
	void End() {
		//��д��Ϸ�����ķ�����ͳ�Ʒ��������öԻ�����ʾ�û���Ϸ���
		int mark=0;
		for(int i=0;i<16;i++)mark+=board[i/4][i%4];
		JOptionPane.showMessageDialog(
				null,
				"��Ϸ��������ĵ÷�Ϊ��"+mark,
				"Game Over",
				JOptionPane. WARNING_MESSAGE
				);
	}

	@Override
	void FrameShow() {
		//��д��Ϸ����ʱÿ����һ���Ľ��������������GUI��Ӧ��λ��
		for(int i=0;i<16;i++){
			int x=i/4,y=i%4;
			f.ChangeBoard(x, y, this.board[x][y]);
		}
	}
	
}
