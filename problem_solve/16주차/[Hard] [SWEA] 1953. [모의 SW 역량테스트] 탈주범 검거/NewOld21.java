
import java.io.*;
import java.util.*;

class Solution {
    static int N;
    static int M;
    static int R;
    static int C;
    static int L;
    static int ans;

    static int[][] map;
    static boolean[][] visited;

    // 상 하 좌 우
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    // 터널 종류별 이동 가능한 방향
    static int[][] tunnel = {
        {},
        {0, 1, 2, 3}, // 1번 : 상하좌우
        {0, 1},       // 2번 : 상하
        {2, 3},       // 3번 : 좌우
        {0, 3},       // 4번 : 상우
        {1, 3},       // 5번 : 하우
        {1, 2},       // 6번 : 하좌
        {0, 2}        // 7번 : 상좌
    };

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            ans = 0;
            map = new int[N][M];
            visited = new boolean[N][M];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            bfs(R, C);

            System.out.println("#" + test_case + " " + ans);
        }
    }

    static void bfs(int x, int y) {

        Queue<Node> q = new ArrayDeque<>();

        q.add(new Node(x, y, 1));
        visited[x][y] = true;
        ans = 1;

        while (!q.isEmpty()) {

            Node node = q.poll();

            // 제한 시간이 되면 더 이상 이동하지 않음
            if (node.time >= L) {
                continue;
            }

            int type = map[node.x][node.y];

            // 현재 터널에서 이동할 수 있는 방향 탐색
            for (int dir : tunnel[type]) {

                int nx = node.x + dx[dir];
                int ny = node.y + dy[dir];

                // 범위 검사
                if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
                    continue;
                }

                // 이미 방문한 위치
                if (visited[nx][ny]) {
                    continue;
                }

                // 터널이 없는 위치
                if (map[nx][ny] == 0) {
                    continue;
                }

                // 현재 방향의 반대 방향
                int reverse;

                if (dir == 0) reverse = 1;
                else if (dir == 1) reverse = 0;
                else if (dir == 2) reverse = 3;
                else reverse = 2;

                // 다음 터널이 연결되어 있는지 확인
                boolean connect = false;

                for (int nextDir : tunnel[map[nx][ny]]) {
                    if (nextDir == reverse) {
                        connect = true;
                        break;
                    }
                }

                if (!connect) {
                    continue;
                }

                visited[nx][ny] = true;
                q.add(new Node(nx, ny, node.time + 1));
                ans++;
            }
        }
    }
}

class Node {
    int x;
    int y;
    int time;

    Node(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
