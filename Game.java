//package GameCode;

import java.awt.event.*;


public abstract class Game extends KeyAdapter {


	protected int[][] board;	//���ڼ�¼��Ϸ9�������е�����
	boolean running=false;		//���ڼ�¼��Ϸ����״̬��true��ʾ������Ϸ

	public Game(){	}

	public void Init(){
		//��Ϸ��ʼ��������������ʼ��һ����Ϸ
		board=new int[4][4];	//����λ�õķ����ʼ��
		running=true;			//����Ϸ����״̬���ó���Ϸ��
		NewStep();				//���ɵ�һ���������
		show();					//����Ϸ״̬�������ʾ
	}


	abstract void End();		//��Ϸ������Ĳ�����������GUI��ƶԽӵ�ʱ��ʵ��
	abstract void FrameShow();	//������ʾ��Ϸ���ݸ��û���������GUI��ƶԽӵ�ʱ��ʵ��

	private boolean CheckEnd(){
		//�˺������ڼ����Ϸʱ������������Ϸ��������false

		for(int x=0;x<3;x++){

			for(int y=0;y<3;y++){
				if(board[x][y]==board[x][y+1]||board[x][y]==0) 
					return true;
				if(board[x][y]==board[x+1][y]||board[x][y]==0) 
					return true;
			}
			//�ȼ�����½�9��������û�����ұ߻�������һ����ͬ�ģ����߲����ڷ����

			if(board[3][x]==board[3][x+1]||board[3][x]==0)
				return true;
			//�ټ������һ����û������������ͬ�ģ����߲����ڷ����

			if(board[x][3]==board[x+1][3]||board[x][3]==0)
				return true;
			//��������ұ�һ����û������������ͬ�ģ����߲����ڷ����

		}

		if(board[3][3]==0)return true;
		//��ʱ�Ѿ�֪��ȫ��λ��û������������ͬ�Ļ��߲����ڷ�������������������Ͻ��Ƿ��з���

		//�������Ͻ�Ҳ�з�������Ϸ������������Ϸ����״̬��Ϊfalse
		return running=false;
	}

	private void NewStep(){
		//�˺�����������Ϸ����ʱ��������µķ���

		int x,y;
		do{
			x=(int)(Math.random()*100)%4;
			y=(int)(Math.random()*100)%4;
		}while(board[x][y]!=0);
		//����ҵ�һ��û�з�����ڵ�λ��

		board[x][y]=(int)(Math.random()*1000)%1000<800?2:4;
		//�������ȡ��λ��������һ���еķ��飬����7��3�ĸ����������2��4

	}

	private void show(){
		//�˺������ڽ���Ϸ�����������ڿ���̨����������ߵ���
		for(int i=0;i<100;i++)System.out.println();
		for(int y=3;y>=0;y--){
			System.out.println("----------------------");
			for(int x=0;x<4;x++){
				System.out.printf("|%4d",board[x][y]);
			}
			System.out.println("|");
		}
		System.out.println("----------------------");

		FrameShow();//���ó�����������Ϸʵʱ��ʾ��ʵ���û�
	}


	private boolean down(){

		//��һ�����ð��������Ƿ��ܽ��У�
		boolean enable=false;
		for (int x=0;x<4;x++){
			int t=0;
			for(int y=3;y>=0;y--){
				if(enable) break;
				if(t==0) 
					t=board[x][y];
				else {
					if(board[x][y]==0||board[x][y]==t) 
						enable=true;
					else 
						t=board[x][y];
				}

			}
		}
		if(!enable)return false;


		//�����㷽�����¶ѻ�
		for(int x=0;x<4;x++)
			for(int y=2;y>=0;y--)
				if(board[x][y]==0){
					for(int t=y;t<3;t++)
						board[x][t]=board[x][t+1];
					board[x][3]=0;

				}

		//��󣬽�������ͬ�ķ����������
		for(int x=0;x<4;x++){
			if(board[x][0]==board[x][1]&&board[x][0]!=0){
				board[x][0]*=2;
				board[x][1]=board[x][2];
				board[x][2]=board[x][3];
				board[x][3]=0;
			}
			if(board[x][1]==board[x][2]&&board[x][1]!=0){
				board[x][1]*=2;
				board[x][2]=board[x][3];
				board[x][3]=0;
			}
			else if(board[x][2]==board[x][3]&&board[x][2]!=0){
				board[x][2]*=2;
				board[x][3]=0;
			}
		}

		return true;
	}

	private void turn(int times){
		//�˺��������ǽ���¼����������ת90�ȣ�����times������ת����
		for(int i=0;i<times;i++){
			int[][] temp=new int[4][4];
			for(int x=0;x<4;x++)
				for(int y=0;y<4;y++)
					temp[x][y]=board[y][3-x];

			board=temp;
		}


	}


	@Override
	public void keyPressed(KeyEvent e) {
		//�˺����������������¼�����Ӧִ����Ϸ�����ϣ����£��������ң�4������

		if(!running) return;		//�����Ϸ״̬����������Ϸ�У���ִ����Ϸ����
		switch(e.getKeyCode()){
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			if(!down())return;//������������ϲ����Ӧ�İ�������ִ�����ϵĲ���
			//����˲��費�ܱ�ִ�У�ֱ�ӷ�����������
			break;

		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			//���ϵĲ�������ת180�Ⱥ�ִ�����µĲ��������ת180�ȣ������������򷽷�ͬ��
			turn(2);
			if(!down()){
				turn(2);
				return;		
			}
			turn(2);
			break;

		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			turn(1);
			if(!down()){
				turn(3);
				return;		
			}
			turn(3);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			turn(3);
			if(!down()){
				turn(1);
				return;		
			}
			turn(1);
			break;
		default:
			break;
		}
		NewStep();					//��������µķ���
		show();						//���˲���Ϸ���н������Լ���ʾ
		if(!CheckEnd()){			//����Ƿ�ﵽ��Ϸ��������������ﵽ���������Ϸ
			System.out.println("Game Over!");
			End();
		}

	}

}
