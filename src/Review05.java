import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "yu912912");

            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String sql = "SELECT * FROM person WHERE id = ?";    // ← 修正
            pstmt = con.prepareStatement(sql);  // ← 修正

            // 5, 6. Select文の実行と結果を格納／代入
            System.out.print("検索キーワードを入力してください > ");    // ← 追記
            String input = keyIn();    // ← 追記


         // PreparedStatementオブジェクトの?に値をセット  // ← 追記
            pstmt.setInt(1, Integer.parseInt(input));  // ← 追記

            rs = pstmt.executeQuery();  // ← 修正




            // 7. 結果を表示する
            while (rs.next()) {
                // Name列の値を取得
                String name = rs.getString("name");
                // Population列の値を取得 ← 追記
                int age = rs.getInt("age");

                // 取得した値を表示
                System.out.println(name);
                System.out.println(age);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 8. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列    // ← 追記
     */
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;

    }

}
