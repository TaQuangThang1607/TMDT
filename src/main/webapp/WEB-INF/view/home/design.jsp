<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thiết kế Áo Dài</title>
    <style>
        body {
            background: #f8fafc;
            font-family: 'Segoe UI', Arial, sans-serif;
        }
        h2 {
            color: #1a237e;
            margin-top: 32px;
            letter-spacing: 1px;
        }
        form {
            max-width: 520px;
            margin: 40px auto;
            padding: 32px 28px 28px 28px;
            border-radius: 16px;
            background: #fffbe7;
            box-shadow: 0 4px 24px rgba(26,35,126,0.08), 0 1.5px 6px rgba(26,35,126,0.05);
        }
        label {
            display: block;
            margin-top: 18px;
            font-weight: 600;
            color: #1a237e;
        }
        select, input[type="file"], textarea {
            width: 100%;
            padding: 10px 12px;
            margin-top: 7px;
            border-radius: 6px;
            border: 1.5px solid #c5cae9;
            background: #f5f7fa;
            font-size: 15px;
            transition: border-color 0.2s;
        }
        select:focus, input[type="file"]:focus, textarea:focus {
            border-color: #ffd600;
            outline: none;
        }
        textarea {
            resize: vertical;
            min-height: 80px;
        }
        button {
            width: 100%;
            padding: 12px 0;
            margin-top: 28px;
            border: none;
            border-radius: 6px;
            background: linear-gradient(90deg, #1a237e 60%, #ffd600 100%);
            color: #fff;
            font-size: 17px;
            font-weight: bold;
            letter-spacing: 1px;
            cursor: pointer;
            box-shadow: 0 2px 8px rgba(26,35,126,0.08);
            transition: background 0.2s, color 0.2s;
        }
        button:hover {
            background: linear-gradient(90deg, #3949ab 60%, #ffe082 100%);
            color: #1a237e;
        }
        ::placeholder {
            color: #b0bec5;
            opacity: 1;
        }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Thiết kế Áo Dài của bạn</h2>

    <div style="max-width: 520px; margin: 0 auto 32px auto;">
        <table style="width:100%; border-collapse:collapse; background:#fffbe7; border-radius:10px; box-shadow:0 2px 8px rgba(26,35,126,0.08); overflow:hidden;">
            <thead style="background:#1a237e;">
                <tr>
                    <th style="color:#fff; padding:10px;">Size</th>
                    <th style="color:#fff; padding:10px;">Dài áo (cm)</th>
                    <th style="color:#fff; padding:10px;">Ngang áo (cm)</th>
                    <th style="color:#fff; padding:10px;">Vòng eo (cm)</th>
                    <th style="color:#fff; padding:10px;">Chiều cao phù hợp (cm)</th>
                    <th style="color:#fff; padding:10px;">Cân nặng phù hợp (kg)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="padding:8px; text-align:center;">S</td>
                    <td style="padding:8px; text-align:center;">120</td>
                    <td style="padding:8px; text-align:center;">38</td>
                    <td style="padding:8px; text-align:center;">60-64</td>
                    <td style="padding:8px; text-align:center;">150-158</td>
                    <td style="padding:8px; text-align:center;">40-48</td>
                </tr>
                <tr style="background:#fffde7;">
                    <td style="padding:8px; text-align:center;">M</td>
                    <td style="padding:8px; text-align:center;">125</td>
                    <td style="padding:8px; text-align:center;">40</td>
                    <td style="padding:8px; text-align:center;">65-69</td>
                    <td style="padding:8px; text-align:center;">158-165</td>
                    <td style="padding:8px; text-align:center;">48-55</td>
                </tr>
                <tr>
                    <td style="padding:8px; text-align:center;">L</td>
                    <td style="padding:8px; text-align:center;">130</td>
                    <td style="padding:8px; text-align:center;">42</td>
                    <td style="padding:8px; text-align:center;">70-74</td>
                    <td style="padding:8px; text-align:center;">165-170</td>
                    <td style="padding:8px; text-align:center;">55-62</td>
                </tr>
                <tr style="background:#fffde7;">
                    <td style="padding:8px; text-align:center;">XL</td>
                    <td style="padding:8px; text-align:center;">135</td>
                    <td style="padding:8px; text-align:center;">44</td>
                    <td style="padding:8px; text-align:center;">75-80</td>
                    <td style="padding:8px; text-align:center;">170-175</td>
                    <td style="padding:8px; text-align:center;">62-70</td>
                </tr>
            </tbody>
        </table>
        <p style="font-size:13px; color:#616161; margin-top:8px; text-align:center;">
            * Số đo chỉ mang tính chất tham khảo, có thể chênh lệch tuỳ theo form dáng từng người.
        </p>
    </div>

    <form action="/submitDesign" method="post" enctype="multipart/form-data">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label for="size">Chọn size áo dài:</label>
        <select id="size" name="size" required>
            <option value="">--Chọn size--</option>
            <option value="S">S (Nhỏ)</option>
            <option value="M">M (Vừa)</option>
            <option value="L">L (Lớn)</option>
            <option value="XL">XL (Rộng)</option>
        </select>

        <label for="color">Chọn màu sắc áo dài:</label>
        <select id="color" name="color" required>
            <option value="">--Chọn màu--</option>
            <option value="red">Đỏ</option>
            <option value="blue">Xanh dương</option>
            <option value="white">Trắng</option>
            <option value="yellow">Vàng</option>
            <option value="pink">Hồng</option>
            <option value="green">Xanh lá</option>
        </select>

        <label for="image">Tải lên hình ảnh thiết kế:</label>
        <input type="file" id="image" name="image" accept="image/*" required />

<label for="description">Mô tả sản phẩm:</label>
<textarea id="description" name="description" rows="4" placeholder="Nhập mô tả sản phẩm bạn muốn thiết kế..." required></textarea>

<button type="submit">Xác nhận</button>
    </form>
</body>
</html>