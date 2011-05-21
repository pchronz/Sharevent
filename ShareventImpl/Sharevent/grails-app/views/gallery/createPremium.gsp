<%--
  Created by IntelliJ IDEA.
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
</head>
<body>
<div style="width: 500px; margin-left: auto; margin-right: auto;">
    <h2>Create a premium gallery</h2>
    <form action="" method="get">
        <p>
            Title
            <br />
            <input name="GalleryName" type="text" size="50" maxlength="250" />
            <br />

            Location<br />
            <input name="GalleryLocation" type="text" size="50" maxlength="250" />
            <br />

            Year
            <select name="GalleryYear" size="1">
                <option>1980</option>
                <option>1981</option>
                <option>1982</option>
                <option>1983</option>
                <option>1984</option>
                <option>1985</option>
                <option>1986</option>
                <option>1987</option>
                <option>1988</option>
                <option>1989</option>
                <option>1990</option>
                <option>1991</option>
                <option>1992</option>
                <option>1993</option>
                <option>1994</option>
                <option>1995</option>
                <option>1996</option>
                <option>1997</option>
                <option>1998</option>
                <option>1999</option>
                <option>2000</option>
                <option>2001</option>
                <option>2002</option>
                <option>2003</option>
                <option>2004</option>
                <option>2005</option>
                <option>2006</option>
                <option>2007</option>
                <option>2008</option>
                <option>2009</option>
                <option>2010</option>
                <option>2011</option>
            </select>

            Month
            <select name="GalleryMonth" size="1">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
                <option>10</option>
                <option>11</option>
                <option>12</option>
            </select>

            Day
            <select name="GalleryDay" size="1">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
                <option>10</option>
                <option>11</option>
                <option>12</option>
                <option>13</option>
                <option>14</option>
                <option>15</option>
                <option>16</option>
                <option>17</option>
                <option>18</option>
                <option>19</option>
                <option>20</option>
                <option>21</option>
                <option>22</option>
                <option>23</option>
                <option>24</option>
                <option>25</option>
                <option>26</option>
                <option>27</option>
                <option>28</option>
                <option>29</option>
                <option>30</option>
                <option>31</option>
            </select>
            <br />

            Your first name
            <br />
            <input name="CreatorFirstName" type="text" size="50" maxlength="250" />
            <br />
            Your last name
            <br />
            <input name="CreatorLastName" type="text" size="50" maxlength="250" />
            <br />
            Your e-mail address (Will be displayed in the gallery)
            <br />
            <input name="CreatorEmail" type="text" size="50" maxlength="250" />
            <br />

            <g:link controller="gallery" action="pay">Next step</g:link>
        </p>
    </form>
    </div>
</body>
</html>